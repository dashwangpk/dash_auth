package com.sprint.dash.api;

import com.sprint.dash.common.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class Demo02 {

    public static void main(String [] args){
        System.out.println(days(1, 1));

    }

    public static int days(int years, int weeks){
        int days = 0;

        Calendar start = Calendar.getInstance();
        start.set(1900, 0, 1);
        long startTimes = start.getTimeInMillis();

        Calendar end = Calendar.getInstance();
        end.set(1900 + years, 11, 31);
        long endTimes = end.getTimeInMillis();

        long oneDateTimes = 24L * 60L * 60L * 1000L;

        int d = 0;
        while(startTimes <= endTimes){
            Date date = new Date(startTimes);
            System.out.println(DateUtil.getDateFormat(date, "yyyy-MM-dd"));

            Calendar calDate = Calendar.getInstance();
            calDate.setTime(date);
            int wDay = calDate.get(Calendar.DAY_OF_WEEK) - 1;
            int mDay = calDate.get(Calendar.DAY_OF_MONTH);

            if(13 == mDay && weeks == wDay){
                days ++;
            }
            d++;
            startTimes = startTimes + oneDateTimes;
        }
        System.out.println("  天数 " + d);

        return days;
    }

}
