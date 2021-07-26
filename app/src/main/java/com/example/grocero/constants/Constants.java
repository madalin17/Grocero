package com.example.grocero.constants;

import android.provider.BaseColumns;

public final class Constants {
    private Constants() {}

    public static final String NEW_NOTE = "New List";
    public static final String DB_NOTES_NAME = "notesDB";
    public static final String DB_NOTES_TABLE = "notes_table";

    public static final String DB_GROCERY_NAME = "grocery_list.db";
    public static final int DB_VERSION = 1;

    public static final class ListEntry implements BaseColumns {
        public static final String DB_ID = "id";
        public static final String DB_TITLE = "title";
        public static final String DB_CONTENT = "content";
        public static final String DB_DATE = "date";
        public static final String DB_TIME = "time";
    }

    public static final class GroceryEntry implements BaseColumns {
        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
