package com.kmsoftware.myschoolapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Lesson;

import java.text.DateFormat;
import java.util.Calendar;

public class LessonDialog extends DialogFragment {

    public Lesson lesson = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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

        builder.setView(view);

        return builder.create();
    }
}
