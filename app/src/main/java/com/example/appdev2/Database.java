package com.example.appdev2;

import android.provider.BaseColumns;

public class Database {

    private Database() {
    }

    public static final class Entry implements BaseColumns {
        public static final String TABLE_NAME = "taskslist";
        public static final String COLUMN_NAME = "task";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
