package com.example.grocero;

import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class Note {
    private long id;
    private String title;
    private SQLiteDatabase database;

    public Note() {

    }

    public Note(String title, SQLiteDatabase database){
        this.title = title;
        this.database = database;
    }

    public Note(long id, String title, SQLiteDatabase database){
        this.id = id;
        this.title = title;
        this.database = database;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
