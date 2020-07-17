package com.cvte.notesync.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH-mm-ss";

    /**
     * Date格式化
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化的time转时间戳
     * @param formatTime
     * @return
     */
    public static long formatTimeToMillis(String formatTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.parse(formatTime, new ParsePosition(0)).getTime();
    }

    /**
     * Date转redis的score
     * @param date
     * @return
     */
    public static Double dateToScore(Date date) {
        long time = date.getTime();
        String s = String.valueOf(time);
        String substring = s.substring(0, 6);
        String substring1 = s.substring(6);
        String r = substring + "." + substring1;
        return new Double(r);
    }
}
