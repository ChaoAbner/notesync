package com.cvte.notesync.utils;

public class RedisKeyUtil {

    private static final String SPLIT = ":";

    /**
     * 用户
     * @param userId
     * @return
     */
    public static String userKey(int userId) {
        return "user" + SPLIT + userId;
    }

    /**
     * 用户的笔记列表
     * @param userId
     * @return
     */
    public static String noteListKey(int userId) {
        return "notes" + SPLIT + userId;
    }

    /**
     * 获取具体的笔记详情
     * @param noteId
     * @return
     */
    public static String noteKey(int noteId) {
        return "note" + SPLIT + noteId;
    }
}
