package com.kmsoftware.myschoolapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.kmsoftware.myschoolapp.adapters.MarksCustomAdapter;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.DatabaseUtilities;

import java.util.ArrayList;

public class MarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MarksActivity.this, AddMarkActivity.class));
            }
        });

        ListView view = findViewById(R.id.mark_list_listview);
        view.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final Mark mark = (Mark) parent.getItemAtPosition(position);

                        PopupMenu menu = new PopupMenu(MarksActivity.this, view);

                        MenuInflater inflater = new MenuInflater(MarksActivity.this);
                        inflater.inflate(R.menu.cab_subjects_menu, menu.getMenu());

                        menu.setOnMenuItemClickListener(
                                new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        if (item.getItemId() == R.id.menu_delete) {
                                            if (item.getItemId() == R.id.menu_delete) {
                                                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (which == DialogInterface.BUTTON_POSITIVE) {
                                                            DatabaseUtilities databaseUtilities = new DatabaseUtilities(MarksActivity.this);
                                                            new DatabaseUtilities(MarksActivity.this).deleteMark(mark);
                                                        }
                                                    }
                                                };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(MarksActivity.this);
                                                builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", onClickListener).setNegativeButton("No", onClickListener).show();

                                            }
                                        } else if (item.getItemId() == R.id.menu_edit) {
                                            Intent intent = new Intent(MarksActivity.this, AddMarkActivity.class);
                                            intent.putExtra("mark_id", mark.get_id());
                                            startActivity(intent);
                                        }

                                        return true;
                                    }
                                }
                        );

                        menu.show();

                        return true;
                    }
                }
        );


        MarksCustomAdapter adapter = new MarksCustomAdapter(this, R.layout.mark_row_list, new DatabaseUtilities(this).getMarksList());

        view.setAdapter(adapter);

    }

}
