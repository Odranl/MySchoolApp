package com.kmsoftware.myschoolapp.model;

/**
 * Created by leona on 28/10/2017.
 */

public class TimeTableEntry {
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    private Subject subject;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    private int dayOfWeek;
    private int lesson;

    private int _id;
    public int getId() {return _id;}
    public TimeTableEntry(Subject subject, int dayOfWeek, int lesson, int id)
    {
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.lesson = lesson;
        _id = id;
    }

}
