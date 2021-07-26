package com.example.grocero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.grocero.constants.Constants.*;

import androidx.annotation.Nullable;

public class GroceryDatabaseHelper extends SQLiteOpenHelper {
    public GroceryDatabaseHelper(@Nullable Context context) {
        super(context, DB_GROCERY_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String groceryQuery = "CREATE TABLE " + GroceryEntry.TABLE_NAME
                + " (" + GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + GroceryEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, "
                + GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

        db.execSQL(groceryQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME);

        onCreate(db);
    }
}
