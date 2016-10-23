package com.example.nguyendhoang.assignment_2.util;

import java.text.DecimalFormat;

/**
 * Created by Nguyen.D.Hoang on 10/20/2016.
 */

public class StringUtil {
    public static String createDateDDMMYY(int dayOfMonth, int monthOfYear, int yy) {
        DecimalFormat df = new DecimalFormat("00");

        String date = df.format(dayOfMonth) + "/" + df.format(monthOfYear + 1) + "/" + yy;
        return date.trim();
    }

    public static String convertToApiDate(String date){
        String[] dateItem = date.split("/");

        String dayOfMonth = dateItem[0];
        String monthOfYear = dateItem[1];
        String year = dateItem[2];

        String dateApi = year + monthOfYear+dayOfMonth;

        return dateApi;
    }

    public static String convertToDateString(String url){
        String subDate = url.substring(0, 10);

        String[] dateItem = subDate.split("-");

        String year = dateItem[0];
        String monthOfYear = dateItem[1];
        String dayOfMonth = dateItem[2];

        return dayOfMonth + "-" + monthOfYear + "-" + year;
    }
}
