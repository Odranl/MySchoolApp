package com.kmsoftware.myschoolapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.CustomAdaptersBuilder;
import com.kmsoftware.myschoolapp.dialogs.DatePickerDialog;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;
import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    Task task = null;
    Subject subject = null;    //The subject selected from the spinner

    ViewHolder views = null;

    public class ViewHolder {
        Spinner subjectsSpinner;
        TextInputEditText taskTitleEditText;
        Button taskDate;
        TextInputEditText taskDescriptionEditText;
        Button saveButton;
        DatePickerDialog datePickerDialog;
        CheckBox checkBox;

        ViewHolder() {
            subjectsSpinner = findViewById(R.id.spinner_subject_add_task);
            taskTitleEditText = findViewById(R.id.edit_text_title_add_task);
            taskDate = findViewById(R.id.edit_text_date_add_task);
            taskDescriptionEditText = findViewById(R.id.add_task_edit_text_description);
            saveButton = findViewById(R.id.button_add_task_save);
            checkBox = findViewById(R.id.add_task_check_box_completed);
            datePickerDialog = new DatePickerDialog(
                    AddTaskActivity.this,
                    new android.app.DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            taskDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(datePickerDialog.getCalendar().getTime()));

                            task.setDateDue(datePickerDialog.getCalendar());
                            view.clearFocus();
                        }
                    },
                    Calendar.getInstance());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        views = new ViewHolder();

        long task_id;

        if ((task_id = getIntent().getLongExtra("id", -1)) != -1) {
            task = Task.findById(Task.class, task_id);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        views.subjectsSpinner.setAdapter(CustomAdaptersBuilder.generateSubjectCustomAdapter(this, SortBy.SUBJECT_NAME, new AdapterDataManipulation<Subject>() {
            @Override
            public List<Subject> loadData() {
                return SugarRecord.listAll(Subject.class);
            }
        }));
        views.subjectsSpinner.setOnItemSelectedListener(
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

        views.taskDate.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    views.datePickerDialog.show();
                }
            }
        );

        views.saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (task.getDateDue() == null) {
                            new AlertDialog.Builder(
                                    AddTaskActivity.this)
                                    .setMessage("Select a date!")
                                    .setNeutralButton("Ok", null)
                                    .show();
                        } else {
                            task.setSubject(subject);
                            task.setDescription(views.taskDescriptionEditText.getText().toString());
                            task.setTitle(views.taskTitleEditText.getText().toString());
                            task.setCompleted(views.checkBox.isChecked());
                            task.save();
                            finish();
                        }
                    }

                }
        );

        if (task != null) {
            loadData();
        } else {
            task = new Task();
        }
    }

    private void loadData() {
        //noinspection unchecked
        List<Subject> subjects = ((BaseCustomAdapter<Subject>)views.subjectsSpinner.getAdapter()).getData();
        for (int i = 0; i < subjects.size(); i++) {
            if (task.getSubject().equals(subjects.get(i))) {
                views.subjectsSpinner.setSelection(i);
                break;
            }
        }
        views.taskTitleEditText.setText(task.getTitle());
        views.taskDate.setText(DateFormatter.getFormattedDate(task.getDateDue().getTime()));
        views.taskDescriptionEditText.setText(task.getDescription());
        views.checkBox.setChecked(task.isCompleted());
        views.datePickerDialog.getDatePicker().init(task.getDateDue().get(Calendar.YEAR), task.getDateDue().get(Calendar.MONTH), task.getDateDue().get(Calendar.DAY_OF_MONTH), null);
    }

}
