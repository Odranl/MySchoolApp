package com.kmsoftware.myschoolapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.kmsoftware.myschoolapp.adapters.SubjectsCustomAdapter;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Lesson;

import java.text.DateFormat;
import java.util.Calendar;

public class AddLessonActivity extends AppCompatActivity {

    Subject subject = null;
    Lesson lesson = null;

    ViewHolder views;

    public class ViewHolder {
        Spinner subjects;
        Spinner dayOfWeeks;
        Button timePicker;
        TextInputEditText lessonLength;
        Button saveButton;

        ViewHolder() {
            subjects = findViewById(R.id.spinnerSubject);
            dayOfWeeks = findViewById(R.id.spinnerDay);
            timePicker = findViewById(R.id.button_time_pick);
            lessonLength = findViewById(R.id.lesson_length_edit_text);
            saveButton = findViewById(R.id.save_lesson_button);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        views = new ViewHolder();

        long lesson_id = getIntent().getLongExtra("id", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SubjectsCustomAdapter subjectArrayAdapter = new SubjectsCustomAdapter(this);

        views.subjects.setAdapter(subjectArrayAdapter);
        views.subjects.setOnItemSelectedListener(
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

        views.dayOfWeeks.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.days_of_week)));

        if (lesson_id != -1) {
            lesson = Lesson.findById(Lesson.class, lesson_id);

            for (int i = 0; i < views.subjects.getAdapter().getCount(); i++) {
                if (views.subjects.getAdapter().getItem(i).equals(lesson.getSubject())) {
                    views.subjects.setSelection(i);
                    break;
                }
            }

            views.dayOfWeeks.setSelection(lesson.getDayOfWeek());
            views.lessonLength.setText(String.valueOf(lesson.getMinutesLength()));


            views.timePicker.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(lesson.getHour().getTime()));
        } else {
            lesson = new Lesson();
        }

        views.saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (areFieldsValid()) {
                            lesson.setSubject(subject);
                            lesson.setDayOfWeek(views.dayOfWeeks.getSelectedItemPosition());
                            lesson.setMinutesLength(Integer.parseInt(views.lessonLength.getText().toString()));

                            lesson.save();

                            AddLessonActivity.this.finish();
                        }
                }
                }
        );

        views.timePicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new TimePickerDialog(
                            AddLessonActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                    if (lesson.getHour() == null) {
                                        lesson.setHour(Calendar.getInstance());
                                    }

                                    lesson.getHour().set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    lesson.getHour().set(Calendar.MINUTE, minute);

                                    views.timePicker.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(lesson.getHour().getTime()));

                                    view.clearFocus();
                                }
                            },
                            0,
                            0,
                            true).show();
                    }
                }
        );
    }

    private boolean areFieldsValid() {
        if (lesson.getHour() == null) {
            views.timePicker.setError("Error: you must pick a time");
            return false;
        }

        if (views.lessonLength.getText().toString().equals("")) {
            views.lessonLength.setError("Error: this field cannot be empty!");
            return false;
        }

        try {
            Integer.parseInt(views.lessonLength.getText().toString());
        } catch (NumberFormatException e) {
            views.lessonLength.setError("Error: insert a valid number!");
            return false;
        }

        return true;
    }

}
