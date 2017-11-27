package com.kmsoftware.myschoolapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.kmsoftware.myschoolapp.model.Subject;

public class AddSubjectActivity extends AppCompatActivity {

    Subject subject = new Subject();

    public class ViewHolder {
        TextInputEditText subjectName;
        TextInputEditText subjectNameShort;
        TextInputEditText subjectTeacher;
        SeekBar red;
        SeekBar blue;
        SeekBar green;
        CardView subjectColorPreview;
        Button saveButton;

        ViewHolder() {
            subjectName = findViewById(R.id.subject_name_edit_text);
            subjectNameShort = findViewById(R.id.subject_name_edit_text_short);
            subjectTeacher = findViewById(R.id.subject_teacher_edit_text);
            red = findViewById(R.id.seekBarRed);
            green = findViewById(R.id.seekBarGreen);
            blue = findViewById(R.id.seekBarBlue);
            subjectColorPreview = findViewById(R.id.viewColorPreview);
            saveButton = findViewById(R.id.save_button);
        }
    }

    ViewHolder views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        views = new ViewHolder();

        long subjectID = this.getIntent().getLongExtra("id", -1);

        if (subjectID != -1) {
            subject = Subject.findById(Subject.class, subjectID);
            SetFields();
        }

        ChangeColorBG();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        views.saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (areFieldsValid()) {
                            subject.setSubjectName(views.subjectName.getText().toString());
                            subject.setSubjectColor(
                                    Color.argb(
                                            255,
                                            views.red.getProgress(),
                                            views.green.getProgress(),
                                            views.blue.getProgress()
                                    )
                            );
                            subject.setSubjectNameShort(views.subjectNameShort.getText().toString());
                            subject.setTeacher(views.subjectTeacher.getText().toString());
                            subject.save();

                            AddSubjectActivity.this.finish();
                        }
                    }

                }
        );

        SeekBar.OnSeekBarChangeListener onFocusChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeColorBG();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        views.red.setOnSeekBarChangeListener(onFocusChangeListener);
        views.green.setOnSeekBarChangeListener(onFocusChangeListener);
        views.blue.setOnSeekBarChangeListener(onFocusChangeListener);
    }

    private void SetFields() {
        views.subjectName.setText(subject.getSubjectName());
        views.subjectNameShort.setText(subject.getSubjectNameShort());
        views.subjectTeacher.setText(subject.getTeacher());
        views.red.setProgress(Color.red(subject.getSubjectColor()));
        views.green.setProgress(Color.green(subject.getSubjectColor()));
        views.blue.setProgress(Color.blue(subject.getSubjectColor()));
    }

    @Override
    public void onBackPressed() {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == Dialog.BUTTON_POSITIVE) {
                    AddSubjectActivity.super.onBackPressed();
                }
            }
        };

        new AlertDialog.Builder(this)
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .setTitle("Discard Changes?")
                .setMessage("Changes will be discarded")
                .show();
    }

    private void ChangeColorBG() {
        views.subjectColorPreview.setCardBackgroundColor(Color.argb(255, views.red.getProgress(), views.green.getProgress(), views.blue.getProgress()));
    }

    private boolean areFieldsValid() {
        if (views.subjectName.getText().toString().equals("")) {
            views.subjectName.setError("Error: this field can't be empty");

            return false;
        }
        if (views.subjectNameShort.getText().toString().equals("")) {
            views.subjectNameShort.setError("Error: this field can't be empty");

            return false;
        }

        return true;
    }

}
