package com.jtao.gankdemo.Activity.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     *  根据传入的日期数据，获取到周几
     *
     * @param dateTime  格式为 yyyy-MM-dd
     * @return
     */
    public static String getDayOfWeek(String dateTime) {
        Calendar calendar = Calendar.getInstance();

        if (dateTime.equals("")) {
            calendar.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }

            if (date != null) {
                calendar.setTime(new Date(date.getTime()));
            }
        }

        int week = calendar.get(Calendar.DAY_OF_WEEK);

        switch (week) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
        }

        return "";
    }
}
