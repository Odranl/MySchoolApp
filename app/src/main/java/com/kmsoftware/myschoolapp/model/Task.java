package com.kmsoftware.myschoolapp.model;

import com.orm.SugarRecord;

import java.util.Calendar;

public class Task extends SugarRecord<Task> {

    //region getters and setters
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Calendar getDateDue() {
        return dateDue;
    }

    public void setDateDue(Calendar dateDue) {
        this.dateDue = dateDue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    //endregion

    private Subject subject;
    private Calendar dateDue;
    private String description;
    private String title;
    private boolean completed;

    public Task() {

    }

    public Task(Subject subject, Calendar dateDue, String description, boolean completed, String title) {
        this.subject = subject;
        this.dateDue = dateDue;
        this.description = description;
        this.completed = completed;
        this.title = title;
    }

    public boolean overdue(Calendar dateToCheck) {
        return !dateDue.after(dateToCheck);
    }
}
