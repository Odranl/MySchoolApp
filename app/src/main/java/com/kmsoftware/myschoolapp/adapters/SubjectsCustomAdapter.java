package com.kmsoftware.myschoolapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsCustomAdapter extends BaseCustomAdapter<Subject> {

    public SubjectsCustomAdapter(@NonNull Context context, @NonNull ArrayList<Subject> objects) {
        super(context, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.subject_row, null);

        Subject subject = (Subject)getItem(position);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(20, 20);
        drawable.setColor(subject.getSubjectColor());

        ((TextView)convertView.findViewById(R.id.dot_color)).setBackground(drawable);

        ((TextView)convertView.findViewById(R.id.subject_name)).setText(subject.getSubjectName());

        ((TextView)convertView.findViewById(R.id.subject_teacher)).setText(subject.getTeacher());

        return convertView;
    }


}
