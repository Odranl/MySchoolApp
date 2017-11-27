package com.kmsoftware.myschoolapp.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateFormatter {
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static long formatDateAsLong(Calendar calendar) {
        return Long.parseLong(dateFormat.format(calendar.getTime()));
    }

    public static Calendar getCalendarFromLong(long date){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(String.valueOf(date)));
            return calendar;
        } catch (ParseException e){
            return null;
        }
    }

    public static Calendar getCalendarFromDatePicker(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar;
    }

    public static String getFormattedDate(int year, int month, int day){
        return DateFormat.getDateInstance().format(DateFormatter.getCalendarFromLong(
                        Long.parseLong(String.format("%d%02d%02d", year, month, day))).getTime());
    }

    public static String getFormattedDate(long date){
        return DateFormat.getDateInstance().format(DateFormatter.getCalendarFromLong(date).getTime());
    }

    public static long getLongFromDate(int year, int month, int day){
        return Long.parseLong(String.format("%d%02d%02d", year, month, day));
    }
}
