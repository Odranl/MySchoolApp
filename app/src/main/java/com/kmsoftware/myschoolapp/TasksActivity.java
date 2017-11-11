package com.kmsoftware.myschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.kmsoftware.myschoolapp.adapters.TasksCustomAdapter;
import com.kmsoftware.myschoolapp.model.Task;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TasksActivity.this, AddTaskActivity.class));
            }
        });

        ListView listView = findViewById(R.id.tasks_list_view);

        TasksCustomAdapter adapter = new TasksCustomAdapter(this, new ArrayList<>(Task.listAll(Task.class)));

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((TasksCustomAdapter)((ListView)findViewById(R.id.tasks_list_view)).getAdapter()).refreshData(new ArrayList<>(Task.listAll(Task.class)));
    }
}
