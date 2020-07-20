package com.cvte.notesync.constant;

/**
 * http响应码
 */
public class NoteHttpCode {

    /**
     * 提示码
     */
    // 用户没有笔记
    public static final int USER_NO_NOTES = 10001;

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

    // 笔记不存在
    public static final int NOTE_NOT_EXIST_ERROR = 40006;

    // 空指针
    public static final int NULL_POINTER_ERROR = 40007;

    // 用户不存在
    public static final int USER_NOT_EXIST = 40008;

    // 笔记删除失败
    public static final int NOTE_DELETE_FAIL = 40009;

    // 系统错误
    public static final int SYSTEM_ERROR = 50001;

    // 其他错误
    public static final int ORDER_ERROR = 41000;

    /**
     * 成功码
     */
    public static final int SUCCESS_CODE = 0;
}
