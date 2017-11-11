package com.kmsoftware.myschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.kmsoftware.myschoolapp.adapters.MarksExpandableCustomAdapter;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarksExpListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marks_exp_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarksExpListActivity.this, AddMarkActivity.class));
            }
        });

        MarksExpandableCustomAdapter adapter = new MarksExpandableCustomAdapter(this, null, null);

        refreshView();

        ExpandableListView view = (findViewById(R.id.mark_list_expandablelistview));

        view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_style_marks_normal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, MarksActivity.class));
        finish();

        return true;
    }

    @Override
    protected void onResume() {
        refreshView();
        super.onResume();
    }

    //Refresh the data on screen
    private void refreshView() {
        HashMap<Subject, List<Mark>> marksMap = new HashMap<>();
        ArrayList<Subject> notEmptySubjects = new ArrayList<>();

        for (Subject subject : Subject.listAll(Subject.class)) {

            ArrayList<Mark> marksForMap = new ArrayList<>();

            for (Mark mark : Mark.listAll(Mark.class)) {
                if (mark.getSubject().equals(subject)) {
                    marksForMap.add(mark);
                }
            }
            if (marksForMap.size() > 0) {
                notEmptySubjects.add(subject);
                marksMap.put(subject, marksForMap);
            }
        }

        ExpandableListView view = ((ExpandableListView) findViewById(R.id.mark_list_expandablelistview));

        MarksExpandableCustomAdapter adapter = (MarksExpandableCustomAdapter) view.getExpandableListAdapter();

        adapter.refreshData(notEmptySubjects, marksMap);
    }
}