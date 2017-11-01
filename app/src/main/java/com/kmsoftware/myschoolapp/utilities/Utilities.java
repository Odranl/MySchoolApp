package com.kmsoftware.myschoolapp.utilities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.kmsoftware.myschoolapp.R;
import com.kmsoftware.myschoolapp.model.Subject;

/**
 * Created by leona on 28/10/2017.
 */

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
}
