package com.kmsoftware.myschoolapp.utilities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;

public class Utilities {
    public static void SetTextViewSubject(TextView view, Subject subject, Configuration configuration)
    {
        view.setText(configuration.orientation == Configuration.ORIENTATION_PORTRAIT ? subject.getSubjectNameShort() : subject.getSubjectName());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(1, subject.getSubjectColor());
        drawable.setColor(subject.getSubjectColor());
        drawable.setCornerRadius(10);
        view.setBackground(drawable);
    }

    public static GradientDrawable generateGradentVariable(Subject subject){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setSize(25, 25);
        drawable.setColor(subject.getSubjectColor());

        return drawable;
    }
}
