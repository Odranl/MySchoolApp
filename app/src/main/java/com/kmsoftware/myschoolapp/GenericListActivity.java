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
import android.widget.FrameLayout;
import android.widget.PopupMenu;

import com.kmsoftware.myschoolapp.adapters.LessonsCustomAdapter;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Lesson;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericListActivity extends AppCompatActivity {

    Class<? extends SugarRecord> objectClass;
    boolean expandableList;
    ListGenerator generator;
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_list);

        Bundle bundle = getIntent().getExtras();

        try {
            if (bundle != null) {
                objectClass = Class.forName(bundle.getString("class", "Subject")).asSubclass(SugarRecord.class);
                expandableList = bundle.getBoolean("expandableList", false);
            }
        } catch (ClassNotFoundException e) {
            objectClass = SugarRecord.class;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                if (objectClass == Subject.class) {
                    intent = new Intent(GenericListActivity.this, AddSubjectActivity.class);
                } else if (objectClass == Mark.class) {
                    intent = new Intent(GenericListActivity.this, AddMarkActivity.class);
                } else if (objectClass == Task.class){
                    intent = new Intent(GenericListActivity.this, AddTaskActivity.class);
                } else {
                    intent = new Intent(GenericListActivity.this, AddLessonActivity.class);
                }

                startActivity(intent);
            }
        });

        if (!expandableList) {
            if (objectClass == Subject.class) {
                generator = new ListGenerator(this, objectClass, SortBy.SUBJECT_NAME, new AdapterDataManipulation<Subject>() {
                    @Override
                    public List<Subject> loadData() {
                        return SugarRecord.listAll(Subject.class);
                    }
                });
                setTitle(R.string.subjects);
            } else if (objectClass == Task.class) {
                generator = new ListGenerator(this, objectClass, SortBy.DATE, new AdapterDataManipulation<Task>() {
                    @Override
                    public List<Task> loadData() {
                        List<Task> tasks = SugarRecord.listAll(Task.class);
                        tasks.add(new Task());
                        return tasks;
                    }
                });
                setTitle(R.string.tasks);
            } else {
                generator = new ListGenerator(this, objectClass, SortBy.DATE, new AdapterDataManipulation<Mark>() {
                    @Override
                    public List<Mark> loadData() {
                        return SugarRecord.listAll(Mark.class);
                    }
                });
                setTitle(R.string.marks);
            }

            ((FrameLayout) findViewById(R.id.root_layout_generic_list)).addView(generator.getListView());
        } else {
            ExpandableListView expandableListView = new ExpandableListView(this);
            if (objectClass == Lesson.class) {
                setTitle(R.string.lessons);

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
                expandableListView.setAdapter(new LessonsCustomAdapter(this, subjectsNotEmpty, lessonHashMap));
            }


            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                                           @Override
                                                           public boolean onChildClick(final ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                                                               final SugarRecord record = objectClass.cast(parent.getExpandableListAdapter().getChild(groupPosition, childPosition));

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
                                                                                               record.delete();
                                                                                               refreshList();
                                                                                           }
                                                                                       }
                                                                                   };
                                                                                   AlertDialog.Builder builder = new AlertDialog.Builder(GenericListActivity.this);
                                                                                   builder.setMessage(R.string.delete_item_dialog_text).setPositiveButton(R.string.yes, onClickListener).setNegativeButton(R.string.no, onClickListener).show();

                                                                               } else if (item.getItemId() == R.id.menu_edit) {
                                                                                   Intent intent;
                                                                                   if (objectClass == Lesson.class) {
                                                                                       intent = new Intent(GenericListActivity.this, AddLessonActivity.class);
                                                                                   } else {
                                                                                       intent = new Intent(GenericListActivity.this, AddMarkActivity.class);
                                                                                   }
                                                                                   intent.putExtra(getString(R.string.id), record.getId());

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

            ((FrameLayout) findViewById(R.id.root_layout_generic_list)).addView(expandableListView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void refreshList() {
        if (generator != null && !expandableList) {
            if (objectClass == Subject.class) {
                generator.refreshData(SortBy.SUBJECT_NAME);
            } else if (objectClass == Task.class) {
                generator.refreshData(SortBy.DATE);
            } else {
                generator.refreshData(SortBy.DATE);
            }
        }
    }
}
