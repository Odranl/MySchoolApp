package com.kmsoftware.myschoolapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Mark;
import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class MarksCustomAdapter extends BaseCustomAdapter<Mark> {


    public MarksCustomAdapter(@NonNull Context context) {
        super(context, R.layout.mark_row_list);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = super.getView(position, convertView, parent);

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

            Drawable drawable1 =  context.getDrawable(R.drawable.colored_dot_drawable);
            drawable1.setTint(mark.getSubject().getSubjectColor());
            dotColorView.setElevation(10);
            dotColorView.setBackground(drawable1);
        }

        ((TextView) convertView.findViewById(R.id.mark_list_subject_name)).setText(mark.getSubject().getSubjectName());
        ((TextView) convertView.findViewById(R.id.mark_list_date)).setText(DateFormat.getDateInstance(DateFormat.LONG).format(mark.getDate().getTime()));

        return convertView;
    }

    @Override
    public ArrayList<Mark> loadData(ArrayList<Mark> data) {
        return new ArrayList<>(SugarRecord.listAll(Mark.class));
    }

    @Override
    protected Comparator<Mark> getComparator() {
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
}
