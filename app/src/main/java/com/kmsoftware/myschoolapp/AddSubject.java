package com.kmsoftware.myschoolapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;

public class AddSubject extends AppCompatActivity {

    DatabaseUtilities dbUtil;

    EditText red = null;
    EditText green = null;
    EditText blue = null;

    int subjectID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);


        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        subjectID = this.getIntent().getIntExtra("subject_id", -1);

        if (subjectID != -1)
            SetFields();

        dbUtil = new DatabaseUtilities(getApplicationContext());

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Button button = findViewById(R.id.save_button);
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ChangeColorBG();
            }
        };
        red.setOnFocusChangeListener(onFocusChangeListener);
        green.setOnFocusChangeListener(onFocusChangeListener);
        blue.setOnFocusChangeListener(onFocusChangeListener);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (subjectID == -1) {
                            dbUtil.addSubject(
                                    ((EditText) findViewById(R.id.subject_name_edit_text)).getText().toString(),
                                    ((EditText) findViewById(R.id.subject_name_edit_text_short)).getText().toString(),
                                    ((EditText) findViewById(R.id.subject_teacher_edit_text)).getText().toString(),
                                    Color.argb(
                                            255,
                                            Integer.parseInt(red.getText().toString()),
                                            Integer.parseInt(green.getText().toString()),
                                            Integer.parseInt(blue.getText().toString())
                                    )
                            );
                        } else {
                            dbUtil.updateSubject(
                                    new Subject(((EditText) findViewById(R.id.subject_name_edit_text)).getText().toString(),
                                            Color.argb(
                                                    255,
                                                    Integer.parseInt(red.getText().toString()),
                                                    Integer.parseInt(green.getText().toString()),
                                                    Integer.parseInt(blue.getText().toString())
                                            ),
                                            ((EditText) findViewById(R.id.subject_name_edit_text_short)).getText().toString(),
                                            ((EditText) findViewById(R.id.subject_teacher_edit_text)).getText().toString(),
                                            subjectID
                                    ));
                        }

                        AddSubject.this.finish();
                    }
                }
        );
    }

    private void SetFields() {

        Subject subject = new DatabaseUtilities(getApplicationContext()).getSubjectByID(subjectID);

        ((EditText) findViewById(R.id.subject_name_edit_text)).setText(subject.getSubjectName());
        ((EditText) findViewById(R.id.subject_name_edit_text_short)).setText(subject.getSubjectNameShort());
        ((EditText) findViewById(R.id.subject_teacher_edit_text)).setText(subject.getTeacher());

        red.setText(Integer.toString(Color.red(subject.getSubjectColor())));
        green.setText(Integer.toString(Color.green(subject.getSubjectColor())));
        blue.setText(Integer.toString(Color.blue(subject.getSubjectColor())));
    }

    private void ChangeColorBG() {
        findViewById(R.id.color_preview).setBackgroundColor(GetColor());
    }

    private int GetColor() {

        int redC = 0;
        int greenC = 0;
        int blueC = 0;

        try {
            redC = Integer.parseInt(red.getText().toString());
        } catch (Exception e) {
            redC = 0;
            red.setText("0");
        }
        try {
            greenC = Integer.parseInt(green.getText().toString());
        } catch (Exception e) {
            greenC = 0;
            green.setText("0");
        }
        try {
            blueC = Integer.parseInt(blue.getText().toString());
        } catch (Exception e) {
            blueC = 0;
            blue.setText("0");
        }
        return Color.argb(
                255,
                redC,
                greenC,
                blueC
        );
    }
}
