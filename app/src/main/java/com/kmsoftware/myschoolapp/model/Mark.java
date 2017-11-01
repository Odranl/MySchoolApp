package com.kmsoftware.myschoolapp.model;

public class Mark {

    private Subject _subject;
    private float _mark;
    private int _id;
    private String _date;
    private String _description;

    public String get_date() {
        return _date;
    }

    public String get_description() {
        return _description;
    }

    public int get_id() {
        return _id;
    }

    public Subject get_subject() {
        return _subject;
    }

    public void set_subject(Subject _subject) {
        this._subject = _subject;
    }

    public float get_mark() {
        return _mark;
    }

    public void set_mark(float _mark) {
        this._mark = _mark;
    }

    public Mark(Subject subject, float mark, String date, String description, int id) {
        this._subject = subject;
        this._mark = mark;
        this._id = id;
        this._description = description;
        this._date = date;
    }
}
