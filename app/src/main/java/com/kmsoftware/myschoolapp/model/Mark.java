package com.kmsoftware.myschoolapp.model;

import com.orm.SugarRecord;

import java.util.Calendar;

public class Mark extends SugarRecord<Mark>{

    private Subject subject;
    private float mark;
    private Calendar date;
    private String description;

    //region getters and setters
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //endregion

    public Mark(){

    }

    public Mark(Subject subject, float mark, Calendar date, String description) {
        this.subject = subject;
        this.mark = mark;
        this.description = description;
        this.date = date;
    }

    public String getMarkReadable(){
        float decimalPart = mark % 1;

        if (decimalPart == .25) return String.valueOf((int)mark) + "+";
        else if (decimalPart == .5) return String.valueOf((int)mark) + "½";
        else if (decimalPart == .75) return String.valueOf((int)mark + 1) + "-";
        else return String.valueOf((int)mark);
    }
}
