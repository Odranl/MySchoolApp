package com.kmsoftware.myschoolapp.adapters;

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

/**
 * Created by leona on 29/10/2017.
 */

public class SubjectsCustomAdapter extends ArrayAdapter<Subject> {

    public SubjectsCustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Subject> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.subject_row, null);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(10, 10);
        drawable.setColor(getItem(position).getSubjectColor());
        ((TextView)convertView.findViewById(R.id.dot_color)).setBackground(drawable);
        ((TextView)convertView.findViewById(R.id.subject_name)).setText(getItem(position).getSubjectName());
        ((TextView)convertView.findViewById(R.id.subject_teacher)).setText(getItem(position).getTeacher());

        return convertView;
    }


}
