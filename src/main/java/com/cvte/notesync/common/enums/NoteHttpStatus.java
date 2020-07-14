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
    SYSTEM_ERROR("系统错误", NoteHttpCode.SYSTEM_ERROR),
    TOKEN_EXPIRE_ERROR("系统错误", NoteHttpCode.TOKEN_EXPIRE),
    ORDER_ERROR("系统错误", NoteHttpCode.ORDER_ERROR),

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
