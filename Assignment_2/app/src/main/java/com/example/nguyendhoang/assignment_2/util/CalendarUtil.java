package com.example.nguyendhoang.assignment_2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nguyen.D.Hoang on 10/20/2016.
 */

public class CalendarUtil {
    public static Calendar convertStringToCalendar(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateParse = null;
        try {
            dateParse = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);

        return calendar;
    }


}
