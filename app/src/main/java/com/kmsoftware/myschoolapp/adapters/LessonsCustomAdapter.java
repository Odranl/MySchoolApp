package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.TimeTableEntry;

import java.util.HashMap;
import java.util.List;

public class LessonsCustomAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<Subject> dataHeader;
    private HashMap<Subject, List<TimeTableEntry>> childData;

    public LessonsCustomAdapter(Context context, List<Subject> dataHeader, HashMap<Subject, List<TimeTableEntry>> childData) {
        this.context = context;
        this.dataHeader = dataHeader;
        this.childData = childData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(dataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TimeTableEntry lesson = (TimeTableEntry) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.lesson_row, null);
        }

        int day = lesson.getDayOfWeek();
        String[] days = convertView.getResources().getStringArray(R.array.days_of_week);

        //Sets the tex of the child element as "[Day] [Lesson number]° lesson" example: "Monday 1° lesson"
        ((TextView) convertView.findViewById(R.id.lesson_lesson)).setText(
                (convertView.getResources().getString(R.string.lesson_text, days[day], lesson.getLesson() + 1)));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return dataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(dataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataHeader.get(groupPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Subject subject = dataHeader.get(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lesson_group_explist, null);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(20, 20);
        drawable.setColor(subject.getSubjectColor());
        convertView.findViewById(R.id.dot_color_lesson).setBackground(drawable);

        ((TextView) convertView.findViewById(R.id.subject_name_lesson)).setText(subject.getSubjectName());

        return convertView;
    }
}
