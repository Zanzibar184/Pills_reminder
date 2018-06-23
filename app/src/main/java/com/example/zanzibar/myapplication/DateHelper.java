package com.example.zanzibar.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {


    public static Date StringtoDate(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date converted = new Date();
        try {
            converted = dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return converted;

    }

    public static Date StringtoDate(String data, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date converted = new Date();
        try {
            converted = dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return converted;

    }
    public static String DateToString(Date data, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(data);

    }
    public static String DateToString(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(data);

    }

    public static String getDaySystem(int add) {

        Date scroll_date = StringtoDate("1900-01-01");
        Calendar c = Calendar.getInstance();
        c.setTime(scroll_date);
        c.add(Calendar.DATE, add);
        scroll_date = c.getTime();

        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String day = outFormat.format(scroll_date);

        return day;
    }
}

