package com.kmsoftware.myschoolapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "TimeTable.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SubjectsTable.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TableTimeTable.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.MarksTable.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedReaderContract.SubjectsTable.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TableTimeTable.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.MarksTable.SQL_CREATE_ENTRIES);
    }
}


