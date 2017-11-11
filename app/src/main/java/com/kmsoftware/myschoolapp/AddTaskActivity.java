package com.kmsoftware.myschoolapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    Task task = new Task();
    Subject subject = new Subject();    //The subject selected from the spinner
    long task_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((task_id = getIntent().getLongExtra("task_id", -1)) != -1) {
            task = Task.findById(Task.class, task_id);
        }

        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.spinner_subject_add_task);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Subject.listAll(Subject.class)));
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

        findViewById(R.id.edit_text_date_add_task).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String buttonText = ((Button) findViewById(R.id.edit_text_date_add_task)).getText().toString();

                        if (!buttonText.equals("")) {
                            Calendar calendarSelectedDate = task.getDateDue();

                            DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_task)).setText(DateFormatter.getFormattedDate(year, month + 1, dayOfMonth));
                                            task.setDateDue(DateFormatter.getCalendarFromLong(DateFormatter.getLongFromDate(year, month, dayOfMonth)));
                                        }
                                    },
                                    calendarSelectedDate.get(Calendar.YEAR),
                                    calendarSelectedDate.get(Calendar.MONTH),
                                    calendarSelectedDate.get(Calendar.DAY_OF_MONTH)
                            );

                            dialog.show();
                        } else {
                            Calendar calendar = Calendar.getInstance();

                            DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            ((Button) findViewById(R.id.edit_text_date_add_task)).setText(DateFormatter.getFormattedDate(year, month + 1, dayOfMonth));
                                            task.setDateDue(DateFormatter.getCalendarFromLong(DateFormatter.getLongFromDate(year, month, dayOfMonth)));
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

        findViewById(R.id.button_add_task_save).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Button) findViewById(R.id.edit_text_date_add_task)).getText().equals("")) {
                            new AlertDialog.Builder(
                                    AddTaskActivity.this).setMessage("Select a date!").setNeutralButton(
                                    "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }
                            ).show();
                        } else {
                            task.setSubject(subject);
                            task.setDescription(((EditText) findViewById(R.id.add_task_edit_text_description)).getText().toString());
                            task.setTitle(((EditText) findViewById(R.id.edit_text_title_add_task)).getText().toString());
                            task.setCompleted(((CheckBox)findViewById(R.id.add_task_check_box_completed)).isChecked());
                            task.save();
                            finish();
                        }

                        finish();
                    }

                }
        );
    }

}
