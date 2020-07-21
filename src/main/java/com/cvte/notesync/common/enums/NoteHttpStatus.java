package com.cvte.notesync.common.enums;

import com.cvte.notesync.constant.NoteHttpCode;

public enum NoteHttpStatus {

    /**
     * 错误
     */
    ILLEGAL_OPERATION("非法操作", NoteHttpCode.ILLEGAL_ERROR),
    PARAMETER_ERROR("参数错误", NoteHttpCode.PARAM_ERROR),
    REJECT_ACCESS("拒绝访问", NoteHttpCode.REJECT_ERROR),
    USER_NOT_LOGIN("未登录", NoteHttpCode.PERMISSION_ERROR),
    USER_NOT_EXIST("用户不存在", NoteHttpCode.USER_NOT_EXIST),
    SYSTEM_ERROR("系统错误", NoteHttpCode.SYSTEM_ERROR),
    TOKEN_EXPIRE_ERROR("token已过期", NoteHttpCode.TOKEN_EXPIRE),
    ORDER_ERROR("未知错误", NoteHttpCode.ORDER_ERROR),
    NULL_POINTER_ERROR("空指针", NoteHttpCode.NULL_POINTER_ERROR),
    NOTE_NOT_EXIST("笔记不存在", NoteHttpCode.NOTE_NOT_EXIST_ERROR),
    NOTE_DELETE_FAIL("笔记删除失败", NoteHttpCode.NOTE_DELETE_FAIL),
    NOTE_INSERT_FAIL("笔记插入失败", NoteHttpCode.NOTE_INSERT_FAIL),
    NOTE_UPDATE_FAIL("笔记更新失败", NoteHttpCode.NOTE_UPDATE_FAIL),

    /**
     * 提示
     */
    USER_NO_NOTES("用户没有笔记", NoteHttpCode.USER_NO_NOTES),

    /**
     * 成功
     */
    SUCCESS("success", NoteHttpCode.SUCCESS_CODE);

    private String errMsg;
    private int errCode;

    NoteHttpStatus(String errMsg, int errCode) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public String getErrMsg(int errCode) {
        for (NoteHttpStatus e : NoteHttpStatus.values()) {
            if (e.getErrCode() == errCode) {
                return errMsg;
            }
        }
        return null;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

}
