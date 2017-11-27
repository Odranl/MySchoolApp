package com.kmsoftware.myschoolapp;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.CustomListView;
import com.kmsoftware.myschoolapp.adapters.LessonsCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.MarksCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.SubjectsCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.TasksCustomAdapter;
import com.kmsoftware.myschoolapp.model.Lesson;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericListActivity extends AppCompatActivity {

    Class<? extends SugarRecord> objectClass;
    boolean expandableList;

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
            CustomListView listView = new CustomListView(this);

            BaseCustomAdapter adapter;
            if (objectClass == Subject.class) {
                adapter = new SubjectsCustomAdapter(this);
                setTitle("Subjects");
            } else if (objectClass == Task.class) {
                adapter = new TasksCustomAdapter(this);
                setTitle("Tasks");
            } else {
                adapter = new MarksCustomAdapter(this);
                setTitle("Marks");
            }

            listView.setAdapter(adapter);

            listView.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {

                            PopupMenu menu = new PopupMenu(view.getContext(), view);
                            menu.setOnMenuItemClickListener(
                                    new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {

                                            if (item.getItemId() == R.id.menu_delete) {
                                                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (which == Dialog.BUTTON_POSITIVE) {
                                                            objectClass.cast(parent.getItemAtPosition(position)).delete();

                                                            ((BaseCustomAdapter)parent.getAdapter()).refreshData();

                                                            ((ListView) view.getParent()).invalidateViews();
                                                        }
                                                    }
                                                };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(GenericListActivity.this);
                                                builder.setMessage("Are you sure you want to delete this item?")
                                                        .setPositiveButton("Yes", onClickListener)
                                                        .setNegativeButton("No", onClickListener)
                                                        .show();
                                            } else if (item.getItemId() == R.id.menu_edit) {
                                                Intent intent;
                                                if (objectClass == Subject.class) {
                                                    intent = new Intent(GenericListActivity.this, AddSubjectActivity.class);
                                                } else if (objectClass == Mark.class) {
                                                    intent = new Intent(GenericListActivity.this, AddMarkActivity.class);
                                                } else {
                                                    intent = new Intent(GenericListActivity.this, AddTaskActivity.class);
                                                }

                                                intent.putExtra("id", ((SugarRecord) parent.getAdapter().getItem((int) id)).getId());

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

            ((FrameLayout) findViewById(R.id.root_layout_generic_list)).addView(listView);
        } else {
            ExpandableListView expandableListView = new ExpandableListView(this);
            if (objectClass == Lesson.class) {
                setTitle("Lessons");
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
                                                                                   builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();

                                                                               } else if (item.getItemId() == R.id.menu_edit) {
                                                                                   Intent intent;
                                                                                   if (objectClass == Lesson.class) {
                                                                                       intent = new Intent(GenericListActivity.this, AddLessonActivity.class);
                                                                                   } else {
                                                                                       intent = new Intent(GenericListActivity.this, AddMarkActivity.class);
                                                                                   }
                                                                                   intent.putExtra("id", record.getId());

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

    private void refreshList() {
        if (!expandableList) {
            ((CustomListView)((FrameLayout) findViewById(R.id.root_layout_generic_list)).getChildAt(0)).refreshData();
        }
    }
}
