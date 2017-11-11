package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;
import com.kmsoftware.myschoolapp.utilities.Utilities;

import java.util.ArrayList;
import java.util.Calendar;

public class TasksCustomAdapter extends BaseCustomAdapter<Task> {

    public TasksCustomAdapter(Context context, ArrayList<Task> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.task_row_list, parent, false);
        }

        Task task = data.get(position);

        TextView dotColor = convertView.findViewById(R.id.task_list_dot_color);
        TextView title = convertView.findViewById(R.id.task_list_title);
        TextView subject = convertView.findViewById(R.id.task_list_subject);
        TextView date = convertView.findViewById(R.id.task_list_date);

        dotColor.setBackground(Utilities.generateGradentVariable(task.getSubject()));

        title.setText(task.getTitle());
        subject.setText(task.getSubject().getSubjectName());
        date.setText(DateFormatter.getFormattedDate(DateFormatter.formatDateAsLong(task.getDateDue())));

        if (task.isCompleted()){
            title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            subject.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            date.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (task.overdue(Calendar.getInstance()) && !task.isCompleted()){
            convertView.setBackgroundColor(Color.RED);
        }

        return convertView;
    }
}
