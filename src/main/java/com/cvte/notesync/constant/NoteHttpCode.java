package com.cvte.notesync.constant;

public class NoteHttpCode {

    /**
     * 错误码
     */
    // 参数错误
    public static final int PARAM_ERROR = 40001;

    // 拒绝访问
    public static final int REJECT_ERROR = 40002;

    // 非法请求
    public static final int ILLEGAL_ERROR = 40003;

    // 未登录
    public static final int PERMISSION_ERROR = 40004;

    // token过期
    public static final int TOKEN_EXPIRE = 40005;

    // 其他错误
    public static final int ORDER_ERROR = 40006;

    // 系统错误
    public static final int SYSTEM_ERROR = 50001;

    /**
     * 成功码
     */
    public static final int SUCCESS_CODE = 0;
}
