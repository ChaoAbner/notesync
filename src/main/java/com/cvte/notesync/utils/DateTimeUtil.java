package com.cvte.notesync.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH-mm-ss";

    public static String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static long formatTimeToMillis(String formatTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.parse(formatTime, new ParsePosition(0)).getTime();
    }
}
