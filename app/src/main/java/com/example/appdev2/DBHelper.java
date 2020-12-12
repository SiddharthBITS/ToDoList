package com.example.appdev2;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdev2.Database.*;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "taskslist.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TASKSLIST_TABLE = "CREATE TABLE " +
                Entry.TABLE_NAME + " (" +
                Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Entry.COLUMN_NAME + " TEXT NOT NULL, " +
                Entry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_TASKSLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
        onCreate(db);
    }
}