package com.cvte.notesync.constant;

import org.springframework.http.HttpStatus;

public enum NoteHttpStatus {
    /**
     * 错误
     */
    ILLEGAL("非法操作", NoteHttpCode.ILLEGAL_ERROR_CODE),
    PARAMEER("参数错误", NoteHttpCode.PARAM_ERROR_CODE),
    REJECT("拒绝访问", NoteHttpCode.REJECT_ERROR_CODE),
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
