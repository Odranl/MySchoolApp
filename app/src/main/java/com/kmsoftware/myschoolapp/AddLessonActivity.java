package com.kmsoftware.myschoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.TimeTableEntry;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;

import java.util.ArrayList;

public class AddLessonActivity extends AppCompatActivity {

    int lesson_id = -1;
    Subject subject = null;
    private final String[] lessons = {"1", "2", "3", "4", "5", "6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        lesson_id = getIntent().getIntExtra("lesson_id", -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Subject> subjects = new DatabaseUtilities(getApplicationContext()).getSubjectsList();
        Spinner spinner = findViewById(R.id.spinnerSubject);

        ArrayAdapter<Subject> subjectArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);

        spinner.setAdapter(subjectArrayAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subject = (Subject) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        final Spinner spinnerDay = findViewById(R.id.spinnerDay);
        spinnerDay.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.days_of_week)));

        final Spinner spinnerLesson = findViewById(R.id.spinnerLesson);
        spinnerLesson.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lessons));

        if (lesson_id != -1) {
            DatabaseUtilities db = new DatabaseUtilities(this);
            TimeTableEntry entry = db.getLessonByID(lesson_id);

            ArrayAdapter<Subject> adapter = (ArrayAdapter<Subject>) spinner.getAdapter();
            int subjectPosition = 0;
            for (int i = 0; i < subjects.size(); i++)
            {
                if (subjects.get(i).equals(entry.getSubject()))
                {
                    subjectPosition = i;
                    break;
                }
            }
            spinner.setSelection(subjectPosition);
            spinnerDay.setSelection(entry.getDayOfWeek());
            spinnerLesson.setSelection(entry.getLesson());
        }

        findViewById(R.id.save_lesson_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseUtilities db = new DatabaseUtilities(getApplicationContext());
                        if (lesson_id == -1)
                            db.AddLesson(subject, spinnerDay.getSelectedItemPosition(), spinnerLesson.getSelectedItemPosition());
                        else
                            db.UpdateLesson(new TimeTableEntry(subject, spinnerDay.getSelectedItemPosition(), spinnerLesson.getSelectedItemPosition(), lesson_id));

                        AddLessonActivity.this.finish();
                    }
                }
        );
    }

}
