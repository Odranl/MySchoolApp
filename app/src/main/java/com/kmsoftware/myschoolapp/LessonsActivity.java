package com.kmsoftware.myschoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;

import com.kmsoftware.myschoolapp.adapters.LessonsCustomAdapter;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lessons);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LessonsActivity.this, AddLessonActivity.class));
            }
        });

        ExpandableListView listView = findViewById(R.id.lessons_list);

        Pair<ArrayList<Subject>, HashMap<Subject, List<Lesson>>> data = getData();

        LessonsCustomAdapter adapter = new LessonsCustomAdapter(this, data.first, data.second);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                             @Override
                                             public boolean onChildClick(final ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                                                 LessonsCustomAdapter lessonsCustomAdapter = (LessonsCustomAdapter) parent.getExpandableListAdapter();
                                                 final Lesson lesson = (Lesson) lessonsCustomAdapter.getChild(groupPosition, childPosition);

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
                                                                                 lesson.delete();
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

        Pair<ArrayList<Subject>, HashMap<Subject, List<Lesson>>> data = getData();

        ((LessonsCustomAdapter) ((ExpandableListView) findViewById(R.id.lessons_list)).getExpandableListAdapter()).refreshData(data.first, data.second);
    }

    private Pair<ArrayList<Subject>, HashMap<Subject, List<Lesson>>> getData() {

        ArrayList<Subject> subjectsNotEmpty = new ArrayList<>();
        HashMap<Subject, List<Lesson>> lessonHashMap = new HashMap<>();

        for (Subject subject : Subject.listAll(Subject.class)) {
            ArrayList<Lesson> lessonSort = new ArrayList<>();

            for (Lesson entry : Lesson.listAll(Lesson.class)) {
                if (subject.equals(entry.getSubject())) {
                    lessonSort.add(entry);
                }
            }
            if (lessonSort.size() > 0) {
                subjectsNotEmpty.add(subject);
                lessonHashMap.put(subject, lessonSort);
            }
        }
        return new Pair<>(subjectsNotEmpty, lessonHashMap);
    }
}
