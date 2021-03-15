package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date stringToDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Date d = null;
        try {
             d = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static String dateToString(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
}
