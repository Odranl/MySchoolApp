package com.kmsoftware.myschoolapp.model;

import android.graphics.Color;

import com.orm.SugarRecord;

public class Subject extends SugarRecord<Subject>{

    //region getter and setters
    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectNameShort() {
        return subjectNameShort;
    }

    public int getSubjectColor() {
        return subjectColor;
    }

    public String getTeacher() { return teacher; }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectNameShort(String subjectNameShort) {
        this.subjectNameShort = subjectNameShort;
    }

    public void setSubjectColor(int subjectColor) {
        this.subjectColor = subjectColor;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    //endregion

    private String subjectName = "";
    private int subjectColor = Color.WHITE;
    private String teacher = "";
    private String subjectNameShort = "";

    public Subject(){

    }

    public Subject(String subjectName, int subjectColor, String subjectNameShort, String teacher) {
        this.subjectNameShort = subjectNameShort;
        this.subjectName = subjectName;
        this.subjectColor = subjectColor;
        this.teacher = teacher;
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