package com.kmsoftware.myschoolapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMarkActivity extends AppCompatActivity {

    private Subject subject = null;
    private long mark_id = -1;
    private ArrayList<Subject> subjects = null;

    Mark mark = new Mark();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);

        subjects = new ArrayList<>(Subject.listAll(Subject.class));

        if ((mark_id = getIntent().getLongExtra("mark_id", -1)) != -1) {
            mark = Mark.findById(Mark.class, mark_id);
            LoadData();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<Subject> subjectArrayAdapter = new ArrayAdapter<Subject>(
                this, android.R.layout.simple_spinner_item,
                subjects
        );

        Spinner spinner = findViewById(R.id.spinner_subject_add_mark);
        spinner.setAdapter(subjectArrayAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subject = (Subject) parent.getAdapter().getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        findViewById(R.id.edit_text_date_add_mark).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String buttonText = ((Button) findViewById(R.id.edit_text_date_add_mark)).getText().toString();

                        if (!buttonText.equals("")) {
                            Calendar calendarSelectedDate = mark.getDate();

                            DatePickerDialog dialog = new DatePickerDialog(AddMarkActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_mark)).setText(DateFormatter.getFormattedDate(year, month + 1, dayOfMonth));
                                            mark.setDate(DateFormatter.getCalendarFromLong(DateFormatter.getLongFromDate(year, month, dayOfMonth)));
                                        }
                                    },
                                    calendarSelectedDate.get(Calendar.YEAR),
                                    calendarSelectedDate.get(Calendar.MONTH),
                                    calendarSelectedDate.get(Calendar.DAY_OF_MONTH)
                            );

                            dialog.show();
                        } else {
                            Calendar calendar = Calendar.getInstance();

                            DatePickerDialog dialog = new DatePickerDialog(AddMarkActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_mark)).setText(DateFormatter.getFormattedDate(year, month + 1, dayOfMonth));
                                            mark.setDate(DateFormatter.getCalendarFromLong(DateFormatter.getLongFromDate(year, month, dayOfMonth)));
                                        }
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );

                            dialog.show();
                        }
                    }
                }
        );

        findViewById(R.id.button_add_mark_save).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Button) findViewById(R.id.edit_text_date_add_mark)).getText().equals("")) {
                            new AlertDialog.Builder(
                                    AddMarkActivity.this).setMessage("Select a date!").setNeutralButton(
                                    "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }
                            ).show();
                        } else {
                            mark.setSubject(subject);
                            mark.setMark(Float.parseFloat(((EditText) findViewById(R.id.edit_text_mark_add_mark)).getText().toString()));
                            mark.setDescription(((EditText) findViewById(R.id.add_mark_edit_text_description)).getText().toString());

                            mark.save();
                            finish();
                        }

                        finish();
                    }

                }
        );
    }

    private void LoadData() {
        for (int i = 0; i < subjects.size(); i++) {
            if (mark.getSubject().equals(subjects.get(i))) {
                ((Spinner) findViewById(R.id.spinner_subject_add_mark)).setSelection(i);
                break;
            }
        }
        ((EditText) findViewById(R.id.edit_text_mark_add_mark)).setText(Float.toString(mark.getMark()));
        ((Button) findViewById(R.id.edit_text_date_add_mark)).setText(DateFormatter.getFormattedDate(DateFormatter.formatDateAsLong((mark.getDate()))));
        ((EditText) findViewById(R.id.add_mark_edit_text_description)).setText(mark.getDescription());
    }
}
