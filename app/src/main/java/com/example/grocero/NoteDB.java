package com.example.grocero;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import static com.example.grocero.constants.Constants.*;

public class NoteDB extends SQLiteOpenHelper {
    public NoteDB(@Nullable Context context) {
        super(context, DB_NOTES_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String listQuery = "CREATE TABLE " + DB_NOTES_TABLE
                + "(" + ListEntry.DB_ID + " INT PRIMARY KEY,"
                + ListEntry.DB_TITLE + " TEXT,"
                + ListEntry.DB_CONTENT + " TEXT,"
                + ListEntry.DB_DATE + " TEXT,"
                + ListEntry.DB_TIME + " TEXT" + ");";

        db.execSQL(listQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NOTES_TABLE);
            onCreate(db);
        }
    }

    public long addGroceryList(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ListEntry.DB_TITLE, note.getTitle());

        return db.insert(DB_NOTES_TABLE,null, cv);
    }
}
