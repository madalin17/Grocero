package com.example.grocero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import static com.example.grocero.constants.Constants.*;

public class AddNote extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mNoteName;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_note);

        mToolbar = findViewById(R.id.noteToolbar);
        mNoteName = findViewById(R.id.note_name);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(NEW_NOTE);

        mNoteName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}