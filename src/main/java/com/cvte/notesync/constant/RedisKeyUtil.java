package com.cvte.notesync.constant;

public class RedisKeyUtil {

    private static final String SPLIT = ":";

    /*
    * 保存用户的key
    * */
    public static String userKey(String userId) {
        return "user" + SPLIT + userId;
    }

    /*
    * 保存用户的笔记列表
    * */
    public static String noteListKey(String userId) {
        return "notes" + SPLIT + userId;
    }

    public static String noteKey(String noteId) {
        return "note" + SPLIT + noteId;
    }
}
