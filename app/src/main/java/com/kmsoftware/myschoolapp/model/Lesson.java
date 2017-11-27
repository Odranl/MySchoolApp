package com.kmsoftware.myschoolapp.model;

import com.orm.SugarRecord;

import java.time.DayOfWeek;
import java.util.Calendar;

public class Lesson extends SugarRecord<Lesson> {

    //region getters and setters
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Calendar getHour() {
        return hour;
    }

    public void setHour(Calendar hour) {
        this.hour = hour;
    }

    public int getMinutesLength() {
        return minutesLength;
    }

    public void setMinutesLength(int minutesLength) {
        this.minutesLength = minutesLength;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    //endregion

    private Subject subject;
    private Calendar hour;
    private int dayOfWeek;
    private int minutesLength;

    public Lesson(){

    }

    public Lesson(Subject subject, int dayOfWeek, Calendar hour, int minutesLength)
    {
        this.subject = subject;
        this.hour = hour;
        this.minutesLength = minutesLength;
        this.dayOfWeek = dayOfWeek;
    }

}
