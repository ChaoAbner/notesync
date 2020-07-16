package com.cvte.notesync.utilstest;

import java.util.Date;

public class TimeUtil {

    public static Double dateToScore(Date date) {
        long time = date.getTime();
        String s = String.valueOf(time);
        String substring = s.substring(0, 6);
        String substring1 = s.substring(6);
        String r = substring + "." + substring1;
        return new Double(r);
    }
}
