package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.enums.SortBy;
import com.kmsoftware.myschoolapp.model.Mark;
import com.kmsoftware.myschoolapp.model.Subject;
import com.kmsoftware.myschoolapp.model.Task;
import com.kmsoftware.myschoolapp.utilities.AdapterDataManipulation;
import com.kmsoftware.myschoolapp.utilities.DateFormatter;
import com.kmsoftware.myschoolapp.utilities.Utilities;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Comparator;

// Contains methods to quickly generate adapters for views like GenericListActivity's ListView
public class CustomAdaptersBuilder {

    public static BaseCustomAdapter<Subject> generateSubjectCustomAdapter(Context context, SortBy sortBy, final AdapterDataManipulation<Subject> manipulation) {
        return new BaseCustomAdapter<Subject>(context, R.layout.subject_row, sortBy, manipulation) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    //noinspection ConstantConditions
                    convertView = ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResourceId(), parent, false);
                }
                Subject subject = getItem(position);

                GradientDrawable drawable = new GradientDrawable();

                drawable.setShape(GradientDrawable.OVAL);
                drawable.setSize(20, 20);
                drawable.setColor(subject.getSubjectColor());

                convertView.findViewById(R.id.dot_color).setBackground(drawable);

                ((TextView) convertView.findViewById(R.id.subject_name)).setText(subject.getSubjectName());
                ((TextView) convertView.findViewById(R.id.subject_teacher)).setText(subject.getTeacher());

                return convertView;
            }

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
        };
    }

    public static BaseCustomAdapter<Mark> generateMarkCustomAdapter(final Context context, SortBy sortBy, AdapterDataManipulation<Mark> manipulation) {
        return new BaseCustomAdapter<Mark>(context, R.layout.mark_row_list, sortBy, manipulation) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResourceId(), parent, false);
                }

                Mark mark = getItem(position);

                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setSize(25, 25);
                drawable.setColor(mark.getSubject().getSubjectColor());

                TextView dotColorView;

                dotColorView = (convertView.findViewById(R.id.mark_list_dot_color));
                dotColorView.setBackground(drawable);
                dotColorView.setText(mark.getMarkReadable());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Drawable drawable1 = getContext().getDrawable(R.drawable.colored_dot_drawable);
                    if (drawable1 != null) {
                        drawable1.setTint(mark.getSubject().getSubjectColor());
                    }
                    dotColorView.setElevation(10);
                    dotColorView.setBackground(drawable1);
                }

                ((TextView) convertView.findViewById(R.id.mark_list_subject_name)).setText(mark.getSubject().getSubjectName());
                ((TextView) convertView.findViewById(R.id.mark_list_date)).setText(DateFormat.getDateInstance(DateFormat.LONG).format(mark.getDate().getTime()));

                return convertView;
            }

            @Override
            public Comparator<Mark> getComparator(SortBy sortBy) {
                return new Comparator<Mark>() {
                    @Override
                    public int compare(Mark o1, Mark o2) {
                        if (o1.getDate().after(o2.getDate())) {
                            return -1;
                        } else if (o1.getDate().before(o2)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                };
            }
        };
    }

    public static BaseCustomAdapter<Task> generateTaskCustomAdapter(Context context, SortBy sortBy, AdapterDataManipulation<Task> manipulation) {
        return new BaseCustomAdapter<Task>(context, R.layout.task_row_list, sortBy, manipulation) {

            @Override
            public Comparator<Task> getComparator(SortBy sortBy) {
                return new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {

                        if (o1.getSubject() == null) {
                            if (o2.isCompleted()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        } else if (o2.getSubject() == null) {
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
                            } else if (o1.isCompleted() && o2.isCompleted()) {
                                if (o1.getDateDue().after(o2.getDateDue())) {
                                    return -1;
                                } else {
                                    return +1;
                                }
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
                if (convertView == null) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResourceId(), parent, false);
                }

                Task task = getItem(position);

                if (task.getSubject() == null) {
                    convertView = new FrameLayout(parent.getContext());

                    convertView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));

                    TextView title = new TextView(getContext());

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(5, 15, 5, 15);

                    title.setLayoutParams(params);
                    title.setText(R.string.completed_tasks_header);
                    title.setTextColor(Color.WHITE);
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    title.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        title.setElevation(30);
                    }

                    ((FrameLayout) convertView).addView(title);

                    return convertView;
                }
                try {
                    TextView dotColor = convertView.findViewById(R.id.task_list_dot_color);
                    TextView title = convertView.findViewById(R.id.task_list_title);
                    TextView subject = convertView.findViewById(R.id.task_list_subject);
                    TextView date = convertView.findViewById(R.id.task_list_date);

                    dotColor.setBackground(Utilities.generateGradentVariable(task.getSubject()));

                    title.setText(task.getTitle());
                    subject.setText(task.getSubject().getSubjectName());
                    date.setText(DateFormatter.getFormattedDate(task.getDateDue().getTime()));

                    if (task.isCompleted()) {
                        title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        subject.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        date.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }

                    if (task.overdue(Calendar.getInstance()) && !task.isCompleted()) {
                        convertView.setBackgroundColor(Color.RED);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return convertView;
            }
        };
    }
}
