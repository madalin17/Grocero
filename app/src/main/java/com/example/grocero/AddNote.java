package com.example.grocero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import static com.example.grocero.constants.Constants.*;

@SuppressWarnings("deprecation")
public class AddNote extends AppCompatActivity {
    private EditText mETName;
    private TextView mTVAmount;

    private int mAmount = 0;

    private SQLiteDatabase mDatabase;
    private GroceryAdapter mAdapter;

    private Toolbar mToolbar;
    private EditText mNoteName;
    private RecyclerView mRecyclerView;

    private Calendar c;
    private String todaysDate;
    private String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_note);

        mToolbar = findViewById(R.id.noteToolbar);
        mNoteName = findViewById(R.id.note_name);
        mRecyclerView = findViewById(R.id.recyclerview_items);

        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(NEW_NOTE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GroceryDatabaseHelper databaseHelper = new GroceryDatabaseHelper(this);
        mDatabase = databaseHelper.getReadableDatabase();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this, getAllItems());
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@android.support.annotation.NonNull android.support.v7.widget.RecyclerView recyclerView,
                                  @android.support.annotation.NonNull android.support.v7.widget.RecyclerView.ViewHolder viewHolder,
                                  @android.support.annotation.NonNull android.support.v7.widget.RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@android.support.annotation.NonNull android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(mRecyclerView);

        mETName = findViewById(R.id.edittext_name);
        mTVAmount = findViewById(R.id.textview_amount);

        Button increase = findViewById(R.id.button_plus);
        Button decrease = findViewById(R.id.button_minus);
        Button add = findViewById(R.id.button_add);

        increase.setOnClickListener(v -> {
            ++mAmount;
            mTVAmount.setText(String.valueOf(mAmount));
        });

        decrease.setOnClickListener(v -> {
            if (mAmount > 0) {
                --mAmount;
                mTVAmount.setText(String.valueOf(mAmount));
            }
        });

        add.setOnClickListener(v -> addItem());

        mNoteName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1)
                + "/" + c.get(Calendar.DAY_OF_MONTH);
        currentTime = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Note deleted.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else if (item.getItemId() == R.id.save) {
            if(mNoteName.getText().length() != 0) {
                Note note = new Note(mNoteName.getText().toString(), todaysDate, currentTime);
                NoteDB db = new NoteDB(this);
                db.addGroceryList(note);

                Toast.makeText(this, "Note saved.", Toast.LENGTH_SHORT).show();
                goToMain();
            } else {
                mNoteName.setError("Cannot left empty!");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void addItem() {
        if (mETName.getText().toString().trim().length() != 0 && mAmount != 0) {
            String mName = mETName.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put(GroceryEntry.COLUMN_NAME, mName);
            contentValues.put(GroceryEntry.COLUMN_AMOUNT, mAmount);
            contentValues.put(GroceryEntry.COLUMN_TIMESTAMP, String.valueOf(mRecyclerView));

            mDatabase.insert(GroceryEntry.TABLE_NAME, null, contentValues);
            mAdapter.swapCursor(getAllItems());

            mETName.getText().clear();
        }
    }

    private void removeItem(long id) {
        mDatabase.delete(GroceryEntry.TABLE_NAME,
                GroceryEntry._ID + "=" + id,
                null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return  mDatabase.query(GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryEntry.COLUMN_TIMESTAMP + " DESC");
    }
}