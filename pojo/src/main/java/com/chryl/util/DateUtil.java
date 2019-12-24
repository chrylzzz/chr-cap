package com.chryl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chryl on 2019/12/23.
 */
public class DateUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String format = simpleDateFormat.format(date);
        return format;
    }


    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }
}
