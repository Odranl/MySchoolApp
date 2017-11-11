package com.kmsoftware.myschoolapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Lesson;

import java.util.ArrayList;

public class AddLessonActivity extends AppCompatActivity {

    long lesson_id = -1;
    Subject subject = null;
    private final String[] lessons = {"1", "2", "3", "4", "5", "6"};

    Lesson lesson = new Lesson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        lesson_id = getIntent().getLongExtra("lesson_id", -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Subject> subjects = new ArrayList<>(Subject.listAll(Subject.class));
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
            lesson = Lesson.findById(Lesson.class, lesson_id);

            int subjectPosition = 0;
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).equals(lesson.getSubject())) {
                    subjectPosition = i;
                    break;
                }
            }
            spinner.setSelection(subjectPosition);
            spinnerDay.setSelection(lesson.getDayOfWeek());
            spinnerLesson.setSelection(lesson.getLesson());
        }

        findViewById(R.id.save_lesson_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        lesson.setSubject(subject);
                        lesson.setDayOfWeek(spinnerDay.getSelectedItemPosition());
                        lesson.setLesson(spinnerLesson.getSelectedItemPosition());

                        lesson.save();

                        AddLessonActivity.this.finish();
                    }
                }
        );
    }

}
