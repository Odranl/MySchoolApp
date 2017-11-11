package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarksExpandableCustomAdapter extends BaseExpandableCustomAdapter<Subject, Mark>{

    public MarksExpandableCustomAdapter(Context context, ArrayList<Subject> subjects, HashMap<Subject, List<Mark>> marks){
        super(context, subjects, marks);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mark_child_explist, null);
        }

        Mark mark = (Mark) getChild(groupPosition, childPosition);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(25, 25);
        drawable.setColor(mark.getSubject().getSubjectColor());

        TextView dotColorView = (convertView.findViewById(R.id.mark_child_dot_color));
        dotColorView.setBackground(drawable);
        dotColorView.setText(mark.getMarkReadable());

        ((TextView) convertView.findViewById(R.id.mark_child_date)).setText(DateFormat.getDateInstance(DateFormat.LONG).format(mark.getDate().getTime()));

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mark_group_list, parent, false);
        }

        Subject subject = dataHeaders.get(groupPosition);

        LinearLayout dotColorView = (convertView.findViewById(R.id.linear_layout_mark_group));
        dotColorView.setBackgroundColor(subject.getSubjectColor());

        ((TextView)convertView.findViewById(R.id.subject_name_mark_group)).setText(subject.getSubjectName());
        return convertView;
    }


}
