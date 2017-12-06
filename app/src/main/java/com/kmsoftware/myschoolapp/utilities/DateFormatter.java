package com.kmsoftware.myschoolapp.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormatter {

    public static Calendar getCalendarFromDatePicker(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar;
    }

    public static String getFormattedDate(Date date){
        return DateFormat.getDateInstance().format(date);
    }
}
