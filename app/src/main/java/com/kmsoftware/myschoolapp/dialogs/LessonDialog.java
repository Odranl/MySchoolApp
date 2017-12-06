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

import com.kmsoftware.myschoolapp.AddTaskActivity;
import com.kmsoftware.myschoolapp.CustomViews.CustomListView;
import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.adapters.CustomAdaptersBuilder;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Lesson;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;
import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class LessonDialog extends DialogFragment {

    public LessonDialog() {
        super();
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Lesson lesson = SugarRecord.findById(Lesson.class, getArguments().getLong(getString(R.string.lesson_id), -1));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.lesson_dialog, null);

        TextView textViewTime = view.findViewById(R.id.dialog_lesson_time);
        Calendar timeFinish = (Calendar)lesson.getHour().clone();

        timeFinish.add(Calendar.MINUTE, lesson.getMinutesLength());

        textViewTime.setText(getString(R.string.lesson_dialog,DateFormat.getTimeInstance(DateFormat.SHORT).format(lesson.getHour().getTime()), DateFormat.getTimeInstance(DateFormat.SHORT).format(timeFinish.getTime()), getResources().getStringArray(R.array.days_of_week)[lesson.getDayOfWeek()]));

        TextView textViewHeader = view.findViewById(R.id.dialog_lesson_header);

        textViewHeader.setText(lesson.getSubject().getSubjectName());
        ((View)textViewHeader.getParent()).setBackgroundColor(lesson.getSubject().getSubjectColor());

        AppBarLayout appBarLayout = view.findViewById(R.id.dialog_lesson_tasks_header);
        appBarLayout.setBackgroundColor(lesson.getSubject().getSubjectColor());

        CustomListView listView = view.findViewById(R.id.task_list_view_main);

        listView.setAdapter(CustomAdaptersBuilder.generateTaskCustomAdapter(view.getContext(), SortBy.DATE, new AdapterDataManipulation<Task>() {
            @Override
            public List<Task> loadData() {
                return SugarRecord.find(Task.class, "subject = ?", String.valueOf(lesson.getSubject().getId()));
            }
        }));

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


                                                        ((ListView) view.getParent()).invalidateViews();
                                                    }
                                                }
                                            };

                                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                                            builder.setMessage(R.string.delete_item_dialog_text)
                                                    .setPositiveButton(R.string.yes, onClickListener)
                                                    .setNegativeButton(R.string.no, onClickListener)
                                                    .show();
                                        } else if (item.getItemId() == R.id.menu_edit) {
                                            Intent intent;

                                            intent = new Intent(view.getContext(), AddTaskActivity.class);

                                            intent.putExtra(getString(R.string.id), ((SugarRecord) parent.getAdapter().getItem((int) id)).getId());

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
