package com.kmsoftware.myschoolapp.db;

import android.provider.BaseColumns;

/**
 * Created by leona on 28/10/2017.
 */

public final class FeedReaderContract {
    private FeedReaderContract() {
    }

    public final class SubjectsTable implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_SUBJECT_NAME = "subject_name";
        public static final String COLUMN_NAME_SUBJECT_NAME_SHORT = "subject_name_short";
        public static final String COLUMN_NAME_SUBJECT_COLOR = "subject_color";
        public static final String COLUMN_NAME_SUBJECT_TEACHER = "subject_teacher";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + SubjectsTable.TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_SUBJECT_NAME + " TEXT," +
                        COLUMN_NAME_SUBJECT_NAME_SHORT + " TEXT," +
                        COLUMN_NAME_SUBJECT_COLOR + " INTEGER," +
                        COLUMN_NAME_SUBJECT_TEACHER + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public final class TableTimeTable implements BaseColumns {
        public static final String TABLE_NAME = "time_table";
        public static final String COLUMN_NAME_SUBJECT_NAME = "subject_name";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_LESSON = "lesson";


        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + TableTimeTable.TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_SUBJECT_NAME + " TEXT," +
                        COLUMN_NAME_DAY + " INTEGER," +
                        COLUMN_NAME_LESSON + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public final class MarksTable implements BaseColumns
    {
        public static final String TABLE_NAME = "marks";

        public static final String COLUMN_NAME_SUBJECT_NAME = "subject_name";
        public static final String COLUMN_NAME_MARK = "mark";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME_SUBJECT_NAME + " TEXT, " +
                        COLUMN_NAME_MARK + " REAL, " +
                        COLUMN_NAME_DATE + " TEXT, " +
                        COLUMN_NAME_DESCRIPTION + "TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}