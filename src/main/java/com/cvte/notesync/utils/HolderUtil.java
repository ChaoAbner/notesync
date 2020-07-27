package com.cvte.notesync.utils;

public class HolderUtil {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        threadLocal.set(userId);
    }

    public static int getUserId() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
