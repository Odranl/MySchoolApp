package com.kmsoftware.myschoolapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.AddMarkActivity;
import com.kmsoftware.myschoolapp.AddSubjectActivity;
import com.kmsoftware.myschoolapp.AddTaskActivity;
import com.kmsoftware.myschoolapp.CustomViews.CustomListView;
import com.kmsoftware.myschoolapp.GenericListActivity;
import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.adapters.BaseCustomAdapter;
import com.kmsoftware.myschoolapp.adapters.TasksCustomAdapter;
import com.kmsoftware.myschoolapp.model.Lesson;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LessonDialog extends DialogFragment {

    public LessonDialog() {
        super();
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Lesson lesson = SugarRecord.findById(Lesson.class, getArguments().getLong("lesson_id", -1));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.lesson_dialog, null);

        TextView textViewTime = view.findViewById(R.id.dialog_lesson_time);
        Calendar timeFinish = (Calendar)lesson.getHour().clone();

        timeFinish.add(Calendar.MINUTE, lesson.getMinutesLength());

        textViewTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(lesson.getHour().getTime()) + " - " + DateFormat.getTimeInstance(DateFormat.SHORT).format(timeFinish.getTime()) + " " + getResources().getStringArray(R.array.days_of_week)[lesson.getDayOfWeek()]);

        TextView textViewHeader = view.findViewById(R.id.dialog_lesson_header);

        textViewHeader.setText(lesson.getSubject().getSubjectName());
        ((View)textViewHeader.getParent()).setBackgroundColor(lesson.getSubject().getSubjectColor());

        AppBarLayout appBarLayout = view.findViewById(R.id.dialog_lesson_tasks_header);
        appBarLayout.setBackgroundColor(lesson.getSubject().getSubjectColor());

        CustomListView listView = view.findViewById(R.id.task_list_view_main);

        TasksCustomAdapter adapter = new TasksCustomAdapter(view.getContext(), SugarRecord.find(Task.class, "subject = ?", String.valueOf(lesson.getSubject().getId())));
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
                                                        ((Task)parent.getItemAtPosition(position)).delete();

                                                        ((BaseCustomAdapter)parent.getAdapter()).refreshData();

                                                        ((ListView) view.getParent()).invalidateViews();
                                                    }
                                                }
                                            };

                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                                            builder.setMessage("Are you sure you want to delete this item?")
                                                    .setPositiveButton("Yes", onClickListener)
                                                    .setNegativeButton("No", onClickListener)
                                                    .show();
                                        } else if (item.getItemId() == R.id.menu_edit) {
                                            Intent intent;

                                            intent = new Intent(view.getContext(), AddTaskActivity.class);

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


        builder.setView(view);



        return builder.create();
    }
}
