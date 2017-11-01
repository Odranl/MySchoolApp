package com.kmsoftware.myschoolapp.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kmsoftware.myschoolapp.db.FeedReaderContract;
import com.kmsoftware.myschoolapp.db.FeedReaderDbHelper;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.TimeTableEntry;

import java.util.ArrayList;


public class DatabaseUtilities {

    private Context context;
    private FeedReaderDbHelper helper;

    //region Columns arrays
    private String[] subjectTableColumns = {
            FeedReaderContract.SubjectsTable._ID,
            FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME,
            FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME_SHORT,
            FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_COLOR,
            FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_TEACHER,
    };

    private String[] lessonTableColumns = {
            FeedReaderContract.TableTimeTable._ID,
            FeedReaderContract.TableTimeTable.COLUMN_NAME_SUBJECT_NAME,
            FeedReaderContract.TableTimeTable.COLUMN_NAME_DAY,
            FeedReaderContract.TableTimeTable.COLUMN_NAME_LESSON
    };

    private String[] marksTableColumns = {
            FeedReaderContract.MarksTable._ID,
            FeedReaderContract.MarksTable.COLUMN_NAME_SUBJECT_NAME,
            FeedReaderContract.MarksTable.COLUMN_NAME_MARK,
            FeedReaderContract.MarksTable.COLUMN_NAME_DATE,
            FeedReaderContract.MarksTable.COLUMN_NAME_DESCRIPTION
    };
    //endregion

    public DatabaseUtilities(Context context) {
        this.context = context;
        helper = new FeedReaderDbHelper(context);
    }

    //region Subjects Methods
    public void addSubject(String name, String nameShort, String teacher, int color) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME, name);//
        values.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME_SHORT, nameShort);
        values.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_COLOR, color);
        values.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_TEACHER, teacher);

        db.insert(FeedReaderContract.SubjectsTable.TABLE_NAME, null, values);

    }

    public ArrayList<Subject> getSubjectsList() {

        ArrayList<Subject> subjects = new ArrayList<>();
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                FeedReaderContract.SubjectsTable.TABLE_NAME,
                subjectTableColumns,
                null,
                null,
                null,
                null,
                FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME
        );
        while (cursor.moveToNext()) {
            subjects.add(
                    new Subject(
                            cursor.getString(cursor.getColumnIndex(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME)),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_COLOR)),
                            cursor.getString(cursor.getColumnIndex(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME_SHORT)),
                            cursor.getString(cursor.getColumnIndex(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_TEACHER)),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.SubjectsTable._ID))));
        }
        cursor.close();
        return subjects;
    }

    public Subject getSubjectByName(String name) {
        for (Subject subj : getSubjectsList()) {
            if (subj.getSubjectName().equals(name))
                return subj;
        }
        return null;
    }

    public Subject getSubjectByID(int id) {
        for (Subject subj : getSubjectsList()) {
            if (subj.getID() == id)
                return subj;
        }
        return null;
    }

    public void deleteSubject(Subject subject) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(FeedReaderContract.SubjectsTable.TABLE_NAME, FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME + "= ?", new String[]{subject.getSubjectName()});
    }

    public void updateSubject(Subject newSubjectData) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME, newSubjectData.getSubjectName());
        contentValues.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_NAME_SHORT, newSubjectData.getSubjectNameShort());
        contentValues.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_TEACHER, newSubjectData.getTeacher());
        contentValues.put(FeedReaderContract.SubjectsTable.COLUMN_NAME_SUBJECT_COLOR, newSubjectData.getSubjectColor());

        String whereClause = FeedReaderContract.SubjectsTable._ID + "= ?";

        db.update(FeedReaderContract.SubjectsTable.TABLE_NAME, contentValues, whereClause, new String[]{Integer.toString(newSubjectData.getID())});

        db.close();
        helper.close();
    }
    //endregion

    //region Lessons Methods
    public void deleteLesson(TimeTableEntry lesson) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(FeedReaderContract.TableTimeTable.TABLE_NAME, FeedReaderContract.TableTimeTable._ID + "= ?", new String[]{Integer.toString(lesson.getId())});
    }

    public TimeTableEntry getLessonByID(int id) {
        for (TimeTableEntry entry : GetLessonList()) {
            if (entry.getId() == id)
                return entry;
        }
        return null;
    }

    public void AddLesson(Subject subject, int day, int lesson) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_SUBJECT_NAME, subject.getSubjectName());//
        values.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_DAY, day);
        values.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_LESSON, lesson);

        db.insert(FeedReaderContract.TableTimeTable.TABLE_NAME, null, values);

    }

    public ArrayList<TimeTableEntry> GetLessonList() {

        ArrayList<TimeTableEntry> lessons = new ArrayList<>();
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                FeedReaderContract.TableTimeTable.TABLE_NAME,
                lessonTableColumns,
                null,
                null,
                null,
                null,
                FeedReaderContract.TableTimeTable.COLUMN_NAME_SUBJECT_NAME
        );
        while (cursor.moveToNext()) {
            lessons.add(
                    new TimeTableEntry(
                            getSubjectByName(cursor.getString(cursor.getColumnIndex(FeedReaderContract.TableTimeTable.COLUMN_NAME_SUBJECT_NAME))),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.TableTimeTable.COLUMN_NAME_DAY)),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.TableTimeTable.COLUMN_NAME_LESSON)),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.TableTimeTable._ID))));
        }
        cursor.close();
        return lessons;
    }

    public void UpdateLesson(TimeTableEntry newLesson) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_SUBJECT_NAME, newLesson.getSubject().getSubjectName());
        contentValues.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_LESSON, newLesson.getLesson());
        contentValues.put(FeedReaderContract.TableTimeTable.COLUMN_NAME_DAY, newLesson.getDayOfWeek());

        String whereClause = FeedReaderContract.TableTimeTable._ID + "= ?";

        db.update(FeedReaderContract.TableTimeTable.TABLE_NAME, contentValues, whereClause, new String[]{Integer.toString(newLesson.getId())});

        db.close();
        helper.close();
    }
    //endregion

    //region Marks Methods
    public ArrayList<Mark> getMarksList() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                FeedReaderContract.MarksTable.TABLE_NAME,
                marksTableColumns,
                null,
                null,
                null,
                null,
                FeedReaderContract.MarksTable.COLUMN_NAME_SUBJECT_NAME
        );

        ArrayList<Mark> marks = new ArrayList<>();

        while (cursor.moveToNext()) {
            marks.add(
                    new Mark(
                            getSubjectByName(cursor.getString(cursor.getColumnIndex(FeedReaderContract.MarksTable.COLUMN_NAME_SUBJECT_NAME))),
                            cursor.getFloat(cursor.getColumnIndex(FeedReaderContract.MarksTable.COLUMN_NAME_MARK)),
                            cursor.getString(cursor.getColumnIndex(FeedReaderContract.MarksTable.COLUMN_NAME_DATE)),
                            cursor.getString(cursor.getColumnIndex(FeedReaderContract.MarksTable.COLUMN_NAME_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndex(FeedReaderContract.MarksTable._ID))
                    )
            );
        }

        cursor.close();
        db.close();
        
        return marks;
    }

    public void updateMark(Mark mark) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String whereClause = FeedReaderContract.MarksTable._ID + " = ?";

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.MarksTable.COLUMN_NAME_SUBJECT_NAME, mark.get_subject().getSubjectName());
        contentValues.put(FeedReaderContract.MarksTable.COLUMN_NAME_MARK, mark.get_mark());
        contentValues.put(FeedReaderContract.MarksTable.COLUMN_NAME_DATE, mark.get_date());
        contentValues.put(FeedReaderContract.MarksTable.COLUMN_NAME_DESCRIPTION, mark.get_description());

        db.update(FeedReaderContract.MarksTable.TABLE_NAME,
                contentValues,
                whereClause,
                new String[]{Integer.toString(mark.get_id())});

        db.close();
    }

    public void addMark(Subject subject, float mark, String date, String description) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.MarksTable.COLUMN_NAME_SUBJECT_NAME, subject.getSubjectName());
        values.put(FeedReaderContract.MarksTable.COLUMN_NAME_MARK, mark);
        values.put(FeedReaderContract.MarksTable.COLUMN_NAME_DATE, date);
        values.put(FeedReaderContract.MarksTable.COLUMN_NAME_DESCRIPTION, description);

        db.insert(FeedReaderContract.MarksTable.TABLE_NAME, null, values);

        db.close();
    }

    public void deleteMark(Mark mark) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String whereClause = FeedReaderContract.MarksTable._ID + " = ?";

        db.delete(FeedReaderContract.MarksTable.TABLE_NAME, whereClause, new String[] { Integer.toString(mark.get_id())});

        db.close();
    }
    //endregion

    public void close() {
        helper.close();
    }
}
