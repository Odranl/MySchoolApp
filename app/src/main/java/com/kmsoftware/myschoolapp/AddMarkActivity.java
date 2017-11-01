package com.kmsoftware.myschoolapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;

import java.util.Calendar;

public class AddMarkActivity extends AppCompatActivity {

    private Subject subject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button = findViewById(R.id.button_add_mark_save);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        ArrayAdapter<Subject> subjectArrayAdapter = new ArrayAdapter<Subject>(
                this, android.R.layout.simple_spinner_item,
                new DatabaseUtilities(this).getSubjectsList()
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

                        Calendar calendar = Calendar.getInstance();
                        String[] date;
                        String buttonText = ((Button) findViewById(R.id.edit_text_date_add_mark)).getText().toString();
                        if (!buttonText.equals("")) {
                            date = buttonText.split("/");

                            DatePickerDialog dialog = new DatePickerDialog(AddMarkActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_mark)).setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                                        }
                                    },

                                    Integer.parseInt(date[2]),
                                    Integer.parseInt(date[1]) - 1,
                                    Integer.parseInt(date[0]));

                            dialog.show();
                        } else {
                            DatePickerDialog dialog = new DatePickerDialog(AddMarkActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_mark)).setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
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
                            Snackbar.make(v, "Lol", Snackbar.LENGTH_LONG);
                        }

                    }
                }
        );
    }

}
