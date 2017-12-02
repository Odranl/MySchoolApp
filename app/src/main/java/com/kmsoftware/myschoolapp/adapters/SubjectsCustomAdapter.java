package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Subject;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SubjectsCustomAdapter extends BaseCustomAdapter<Subject> {

    @Override
    public Comparator<Subject> getComparator(final SortBy sortBy) {
        return new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                if (sortBy == SortBy.SUBJECT_NAME) {
                    return o1.getSubjectName().compareTo(o2.getSubjectName());
                } else {
                    return o1.getTeacher().compareTo(o2.getTeacher());
                }
            }
        };
    }

    public SubjectsCustomAdapter(@NonNull Context context, SortBy sortBy) {
        super(context, R.layout.subject_row, SugarRecord.listAll(Subject.class));
        sortData(getComparator(sortBy));
    }

    public SubjectsCustomAdapter(Context context, List<Subject> data, SortBy sortBy) {
        super(context, R.layout.subject_row, data);
        sortData(getComparator(sortBy));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        Subject subject = getItem(position);

        GradientDrawable drawable = new GradientDrawable();

        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(20, 20);
        drawable.setColor(subject.getSubjectColor());

        convertView.findViewById(R.id.dot_color).setBackground(drawable);

        ((TextView)convertView.findViewById(R.id.subject_name)).setText(subject.getSubjectName());
        ((TextView)convertView.findViewById(R.id.subject_teacher)).setText(subject.getTeacher());

        return convertView;
    }
}
