package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;
import com.kmsoftware.myschoolapp.utilities.Utilities;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class TasksCustomAdapter extends BaseCustomAdapter<Task> {

    public TasksCustomAdapter(Context context) {
        super(context, R.layout.task_row_list);
    }

    @Override
    protected Comparator<Task> getComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {

                if (o1 == null) {
                    if (o2.isCompleted()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (o2 == null) {
                    if (o1.isCompleted()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    if (o1.isCompleted() && !o2.isCompleted()) {
                        return +1;
                    } else if (!o1.isCompleted() && o2.isCompleted()) {
                        return -1;
                    } else {
                        if (o1.getDateDue().before(o2.getDateDue())) {
                            return -1;
                        } else {
                            return +1;
                        }
                    }

                }
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = super.getView(position, convertView, parent);

        Task task = getItem(position);

        if (task == null) {
            convertView = new FrameLayout(parent.getContext());

            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            TextView title = new TextView(context);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(5,15,5,15);

            title.setLayoutParams(params);
            title.setText(R.string.completed_tasks_header);
            title.setTextColor(Color.WHITE);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                title.setElevation(30);
            }

            ((FrameLayout)convertView).addView(title);

            return convertView;
        }

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

    @Override
    public ArrayList<Task> loadData(ArrayList<Task> data) {

        data = new ArrayList<>(SugarRecord.listAll(Task.class));

        data.add(null);

        return data;
    }
}
