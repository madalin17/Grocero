package com.example.grocero;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper;
import static com.example.grocero.constants.Constants.*;

public class GroceryActivity extends AppCompatActivity {
    private EditText mETName;
    private TextView mTVAmount;

    private int mAmount = 0;

    private SQLiteDatabase mDatabase;
    private GroceryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_note);

        GroceryDatabaseHelper databaseHelper = new GroceryDatabaseHelper(this);
        mDatabase = databaseHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

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
    }

    private void addItem() {
        if (mETName.getText().toString().trim().length() != 0 && mAmount != 0) {
            String mName = mETName.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put(GroceryEntry.COLUMN_NAME, mName);
            contentValues.put(GroceryEntry.COLUMN_AMOUNT, mAmount);

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
