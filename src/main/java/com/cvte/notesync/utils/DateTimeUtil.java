package com.cvte.notesync.utils;

import java.math.BigDecimal;
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
     * 字符串时间戳转Date
     * @param timeStamp
     * @return
     */
    public static Date TimeStampToDate(long timeStamp) {
        return new Date(timeStamp);
    }

    /**
     * Date转redis的score
     * @param date
     * @return
     */
    @Deprecated
    public static Double dateToScore(Date date) {
        long time = date.getTime();
        String s = String.valueOf(time);
        String substring = s.substring(0, 6);
        String substring1 = s.substring(6);
        String r = substring + "." + substring1;
        return new Double(r);
    }

    /**
     * 保存的分数转date
     * @param score
     * @return
     */
    public static Date ScoreToDate(double score) {
        BigDecimal bigDecimal = new BigDecimal(score);
        String s = bigDecimal.toPlainString();
        return new Date(Long.parseLong(s));
    }

    /**
     * 保存的分数转时间戳
     * @param score
     * @return
     */
    public static long ScoreToMillis(double score) {
        Date date = ScoreToDate(score);
        return date.getTime();
    }
}
