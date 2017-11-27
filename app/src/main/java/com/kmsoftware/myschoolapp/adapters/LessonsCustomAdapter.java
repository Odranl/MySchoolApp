package com.kmsoftware.myschoolapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Lesson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class LessonsCustomAdapter extends BaseExpandableCustomAdapter<Subject, Lesson> {

    public LessonsCustomAdapter(Context context, List<Subject> dataHeader, HashMap<Subject, List<Lesson>> childData) {
        super(context, dataHeader, childData);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Lesson lesson = (Lesson) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater;
            if ((inflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))) != null) {
                convertView = inflater.inflate(R.layout.lesson_row, null);
            }
        }

        if (convertView != null) {
            int day = lesson.getDayOfWeek();

            String[] days = convertView.getResources().getStringArray(R.array.days_of_week);

            ((TextView) convertView.findViewById(R.id.lesson_lesson)).setText(
                    (days[day] + " " + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(lesson.getHour().getTime()))
            );
        }

        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Subject subject = dataHeaders.get(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            convertView = inflater.inflate(R.layout.lesson_group_explist, null);
        }

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(20, 20);
        drawable.setColor(subject.getSubjectColor());
        convertView.findViewById(R.id.dot_color_lesson).setBackground(drawable);

        ((TextView) convertView.findViewById(R.id.subject_name_lesson)).setText(subject.getSubjectName());

        return convertView;
    }
}
