package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Mark;

import java.util.ArrayList;

public class MarksCustomAdapter extends ArrayAdapter<Mark>{

    public MarksCustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Mark> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mark_row_list, null);
        }

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(25, 25);
        drawable.setColor(getItem(position).get_subject().getSubjectColor());

        TextView dotColorView = (convertView.findViewById(R.id.mark_list_dot_color));
        dotColorView.setBackground(drawable);
        dotColorView.setText(Float.toString(getItem(position).get_mark()));

        ((TextView)convertView.findViewById(R.id.mark_list_subject_name)).setText(getItem(position).get_subject().getSubjectName());
        ((TextView)convertView.findViewById(R.id.mark_list_date)).setText(getItem(position).get_date());

        return convertView;
    }


}
