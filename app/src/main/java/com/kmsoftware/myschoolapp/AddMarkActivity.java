package com.kmsoftware.myschoolapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;

import com.kmsoftware.myschoolapp.CustomViews.CustomSpinner;
import com.kmsoftware.myschoolapp.adapters.SubjectsCustomAdapter;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AddMarkActivity extends AppCompatActivity {

    private Subject subject = null;
    Mark mark = null;
    ViewHolder views = null;

    public class ViewHolder {
        CustomSpinner subjects;
        TextInputEditText markEditText;
        Button datePicker;
        TextInputEditText description;
        com.kmsoftware.myschoolapp.dialogs.DatePickerDialog datePickerDialog;
        Button saveButton;

        ViewHolder() {
            subjects = findViewById(R.id.spinner_subject_add_mark);
            markEditText = findViewById(R.id.edit_text_mark_add_mark);
            datePicker = findViewById(R.id.edit_text_date_add_mark);
            description = findViewById(R.id.add_mark_edit_text_description);
            saveButton = findViewById(R.id.button_add_mark_save);
            datePickerDialog = new com.kmsoftware.myschoolapp.dialogs.DatePickerDialog(
                    AddMarkActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            datePicker.setText(DateFormat
                                    .getDateInstance(DateFormat.LONG)
                                    .format(datePickerDialog.getCalendar().getTime()));

                            mark.setDate(datePickerDialog.getCalendar());

                            view.clearFocus();
                        }
                    },
                    Calendar.getInstance());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);

        views = new ViewHolder();

        long mark_id;
        if ((mark_id = getIntent().getLongExtra("id", -1)) != -1) {
            mark = Mark.findById(Mark.class, mark_id);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        views.subjects.setOnItemSelectedListener(
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

        views.datePicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        views.datePickerDialog.show();
                    }
                }
        );

        views.saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mark.getDate() == null) {
                            new AlertDialog.Builder(
                                    AddMarkActivity.this).setMessage("Select a date!")
                                    .setNeutralButton("Ok", null)
                                    .show();
                        } else {
                            mark.setSubject(subject);
                            mark.setMark(Float.parseFloat(views.markEditText.getText().toString()));
                            mark.setDescription(views.description.getText().toString());

                            mark.save();
                            finish();
                        }

                    }

                }
        );

        if (mark != null) {
            LoadData();
        } else {
            mark = new Mark();
        }
    }

    private void LoadData() {
        List<Subject> subjectList = ((SubjectsCustomAdapter)views.subjects.getAdapter()).getData();

        for (int i = 0; i < subjectList.size(); i++) {
            if (mark.getSubject().equals(subjectList.get(i))) {
                views.subjects.setSelection(i);
                break;
            }
        }

        views.markEditText.setText(String.valueOf(mark.getMark()));
        views.datePicker.setText(DateFormatter.getFormattedDate(DateFormatter.formatDateAsLong((mark.getDate()))));
        views.description.setText(mark.getDescription());
        views.datePickerDialog.getDatePicker().init(mark.getDate().get(Calendar.YEAR), mark.getDate().get(Calendar.MONTH), mark.getDate().get(Calendar.DAY_OF_MONTH), null);
    }
}
