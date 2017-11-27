package com.kmsoftware.myschoolapp.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kmsoftware.myschoolapp.utilities.DateFormatter;

import java.util.Calendar;

public class DatePickerDialog extends android.app.DatePickerDialog{


    public DatePickerDialog(@NonNull Context context, OnDateSetListener listener, Calendar calendar) {
        super(context,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    public Calendar getCalendar() {
        return DateFormatter.getCalendarFromDatePicker(getDatePicker().getYear(), getDatePicker().getMonth(), getDatePicker().getDayOfMonth());
    }

}
