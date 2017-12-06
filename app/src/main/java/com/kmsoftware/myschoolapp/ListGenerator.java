package com.kmsoftware.myschoolapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kmsoftware.myschoolapp.CustomViews.CustomListView;
import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.CustomAdaptersBuilder;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;
import com.orm.SugarRecord;

public class ListGenerator {
    private CustomListView listView;
    private PopupMenu popupMenu;
    private AlertDialog.Builder dialog;
    private Context context;
    private Intent intent;
    public Context getContext() {
        return context;
    }

    CustomListView getListView() {
        return listView;
    }

    ListGenerator(Context context, final Class<? extends SugarRecord> objectClass, final SortBy sortBy, AdapterDataManipulation manipulation) {
        this.context = context;
        listView = new CustomListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        BaseCustomAdapter adapter;

        if (objectClass == Subject.class) {
            adapter = CustomAdaptersBuilder.generateSubjectCustomAdapter(context, sortBy, manipulation);
            intent = new Intent(context, AddSubjectActivity.class);
        } else if (objectClass == Mark.class) {
            adapter = CustomAdaptersBuilder.generateMarkCustomAdapter(context, sortBy, manipulation);
            intent = new Intent(context, AddMarkActivity.class);
        } else {
            adapter = CustomAdaptersBuilder.generateTaskCustomAdapter(context, sortBy, manipulation);
            intent = new Intent(context, AddTaskActivity.class);
        }

        dialog = new AlertDialog.Builder(context);
        dialog.setMessage(R.string.delete_item_dialog_text);


        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                if (item.getItemId() == R.id.menu_delete) {
                                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which == Dialog.BUTTON_POSITIVE) {
                                                ((SugarRecord)(parent.getAdapter().getItem(position))).delete();
                                                ((BaseCustomAdapter)parent.getAdapter()).setData(sortBy);
                                            }
                                        }
                                    };
                                    dialog.setPositiveButton(R.string.yes, onClickListener)
                                            .setNegativeButton(R.string.no, onClickListener)
                                            .show();

                                } else if (item.getItemId() == R.id.menu_edit) {
                                    intent.removeExtra(parent.getContext().getString(R.string.id));
                                    intent.putExtra(parent.getContext().getString(R.string.id), ((SugarRecord)(parent.getAdapter().getItem(position))).getId());
                                    startActivity();
                                }

                                return true;
                            }
                        }
                );

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.cab_subjects_menu, popupMenu.getMenu());

                popupMenu.show();
                return true;
            }
        });
    }

    private void startActivity() {
        ((Activity)context).startActivityForResult(intent, -1);
    }

    private BaseCustomAdapter getAdapter() {
        return (BaseCustomAdapter)listView.getAdapter();
    }

    public void refreshData(SortBy sortBy) {
        getAdapter().setData(sortBy);
    }
}