package com.kmsoftware.myschoolapp.model;

import com.orm.SugarRecord;

public class Lesson extends SugarRecord<Lesson> {

    //region getters and setters
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }
    //endregion

    private Subject subject;
    private int dayOfWeek;
    private int lesson;

    public Lesson(){

    }

    public Lesson(Subject subject, int dayOfWeek, int lesson)
    {
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.lesson = lesson;
    }

}
