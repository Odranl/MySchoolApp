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
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MarksCustomAdapter extends BaseCustomAdapter<Mark> {


    public MarksCustomAdapter(@NonNull Context context, @NonNull ArrayList<Mark> marks) {
        super(context, marks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mark_row_list, null);
        }

        Mark mark = (Mark) getItem(position);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(25, 25);
        drawable.setColor(mark.getSubject().getSubjectColor());

        TextView dotColorView = (convertView.findViewById(R.id.mark_list_dot_color));
        dotColorView.setBackground(drawable);
        dotColorView.setText(mark.getMarkReadable());

        ((TextView) convertView.findViewById(R.id.mark_list_subject_name)).setText(mark.getSubject().getSubjectName());
        ((TextView) convertView.findViewById(R.id.mark_list_date)).setText(DateFormat.getDateInstance(DateFormat.LONG).format(mark.getDate().getTime()));

        return convertView;
    }
}
