package com.cvte.notesync.common.response;

import com.cvte.notesync.constant.NoteHttpCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;

    private String msg;

    private Object data;

    Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    Result(int code, String msg) {
        this(code, msg, null);
    }

    public static Result success() {
        return new Result(NoteHttpCode.SUCCESS_CODE, "success");
    }

    public static Result success(String msg) {
        return new Result(NoteHttpCode.SUCCESS_CODE, msg);
    }

    public static Result success(Object data) {
        return new Result(NoteHttpCode.SUCCESS_CODE, "success", data);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }
}
