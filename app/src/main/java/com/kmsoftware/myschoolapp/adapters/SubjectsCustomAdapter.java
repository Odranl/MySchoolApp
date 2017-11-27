package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Comparator;

public class SubjectsCustomAdapter extends BaseCustomAdapter<Subject> {

    public SubjectsCustomAdapter(@NonNull Context context) {
        super(context, R.layout.subject_row);
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

    @Override
    public ArrayList<Subject> loadData(ArrayList<Subject> data) {
        return new ArrayList<>(SugarRecord.listAll(Subject.class));
    }

    @Override
    protected Comparator<Subject> getComparator() {
        return new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getSubjectName().compareTo(o2.getSubjectName());
            }
        };
    }
}
