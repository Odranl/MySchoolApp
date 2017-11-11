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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.kmsoftware.myschoolapp.adapters.SubjectsCustomAdapter;
import com.kmsoftware.myschoolapp.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    List<Subject> subjects = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Subjects");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subjects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddSubjectActivity.class));
            }
        });

        subjects = Subject.listAll(Subject.class);

        ListView view = findViewById(R.id.list);

        SubjectsCustomAdapter adapter = new SubjectsCustomAdapter(
                this, new ArrayList<>(subjects)
        );

        view.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(final AdapterView<?> adapterView, final View v, final int position, final long id) {

                        PopupMenu menu = new PopupMenu(v.getContext(), v);
                        menu.setOnMenuItemClickListener(
                                new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        if (item.getItemId() == R.id.menu_delete) {
                                            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (which == dialog.BUTTON_POSITIVE) {
                                                        ((Subject) adapterView.getItemAtPosition(position)).delete();

                                                        subjects = new ArrayList<>(Subject.listAll(Subject.class));

                                                        ((ListView) v.getParent()).invalidateViews();
                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(SubjectsActivity.this);
                                            builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();

                                        } else if (item.getItemId() == R.id.menu_edit) {
                                            Intent intent = new Intent(SubjectsActivity.this, AddSubjectActivity.class);
                                            intent.putExtra("subject_id", subjects.get((int) id).getId());

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
        view.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SubjectsCustomAdapter adapter = (SubjectsCustomAdapter)((ListView) findViewById(R.id.list)).getAdapter();
        adapter.refreshData(new ArrayList<>(Subject.listAll(Subject.class)));
    }
}
