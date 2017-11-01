package com.kmsoftware.myschoolapp.model;

import android.graphics.Color;

/**
 * Created by leona on 28/10/2017.
 */

public class Subject {
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    private String subjectName = "";

    public String getSubjectNameShort() {
        return subjectNameShort;
    }

    private String subjectNameShort = "";

    public int getSubjectColor() {
        return subjectColor;
    }

    public void setSubjectColor(int subjectColor) {
        this.subjectColor = subjectColor;
    }

    private int subjectColor = Color.WHITE;

    public String getTeacher() { return teacher; }

    private String teacher;

    public int getID() {return _ID;}
    private int _ID;

    public Subject(String subjectName, int subjectColor, String subjectNameShort, String teacher, int id) {
        this.subjectNameShort = subjectNameShort;
        this.subjectName = subjectName;
        this.subjectColor = subjectColor;
        this.teacher = teacher;
        _ID = id;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Subject)obj).getSubjectName().equals(subjectName);
    }

    @Override
    public String toString() {
        return subjectName;
    }
}