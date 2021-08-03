package com.example.grocero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
                + ListEntry.DB_CONTENT + " TEXT" + ");";

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
        cv.put(ListEntry.DB_DATE,note.getDate());
        cv.put(ListEntry.DB_TIME,note.getTime());

        return db.insert(DB_NOTES_TABLE,null, cv);
    }

    public Note getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {ListEntry.DB_ID, ListEntry.DB_TITLE};
        Cursor cursor=  db.query(DB_NOTES_TABLE, query,
                ListEntry.DB_ID+"=?", new String[]{String.valueOf(id)},
                null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
    }

    public List<Note> getAllNotes(){
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DB_NOTES_TABLE + " ORDER BY " + ListEntry.DB_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                Note note = new Note(
                        Long.parseLong(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        return allNotes;
    }
}
