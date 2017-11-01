package com.kmsoftware.myschoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.kmsoftware.myschoolapp.adapters.LessonsCustomAdapter;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.TimeTableEntry;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    ArrayList<TimeTableEntry> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LessonsActivity.this, AddLessonActivity.class));
            }
        });

        ExpandableListView listView = findViewById(R.id.lessons_list);
        lessons = new ArrayList<>(new DatabaseUtilities(this).GetLessonList());
        ArrayList<Subject> subjects = new DatabaseUtilities(this).getSubjectsList();
        HashMap<Subject, List<TimeTableEntry>> lessonHashMap = new HashMap<>();

        for (Subject subject : subjects) {
            ArrayList<TimeTableEntry> lessonSort = new ArrayList<>();

            for (TimeTableEntry entry : lessons) {
                if (subject.equals(entry.getSubject())) {
                    lessonSort.add(entry);
                }
            }
            lessonHashMap.put(subject, lessonSort);
        }
        LessonsCustomAdapter adapter = new LessonsCustomAdapter(this, subjects, lessonHashMap);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                 @Override
                 public boolean onChildClick(final ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                     LessonsCustomAdapter lessonsCustomAdapter = (LessonsCustomAdapter) parent.getExpandableListAdapter();
                     final TimeTableEntry lesson = (TimeTableEntry) lessonsCustomAdapter.getChild(groupPosition, childPosition);

                     PopupMenu menu = new PopupMenu(v.getContext(), v);
                     menu.setOnMenuItemClickListener(
                             new PopupMenu.OnMenuItemClickListener() {
                                 @Override
                                 public boolean onMenuItemClick(MenuItem item) {
                                     if (item.getItemId() == R.id.menu_delete) {
                                         DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                             @Override
                                             public void onClick(DialogInterface dialog, int which) {
                                                 if (which == AlertDialog.BUTTON_POSITIVE) {
                                                     DatabaseUtilities databaseUtilities = new DatabaseUtilities(LessonsActivity.this.getApplicationContext());
                                                     databaseUtilities.deleteLesson(lesson);
                                                     parent.invalidateViews();
                                                 }
                                             }
                                         };
                                         AlertDialog.Builder builder = new AlertDialog.Builder(LessonsActivity.this);
                                         builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();

                                     } else if (item.getItemId() == R.id.menu_edit) {
                                         Intent intent = new Intent(LessonsActivity.this, AddLessonActivity.class);
                                         intent.putExtra("lesson_id", lesson.getId());

                                         startActivity(intent);
                                     }

                                     return true;
                                 }
                             }
                     );
                     MenuInflater inflater = menu.getMenuInflater();
                     inflater.inflate(R.menu.cab_subjects_menu, menu.getMenu());
                     menu.show();
                     return true;
                 }
             }
        );

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lessons = new ArrayList<>(new DatabaseUtilities(this).GetLessonList());
        ((ListView) findViewById(R.id.lessons_list)).invalidateViews();
    }
}
