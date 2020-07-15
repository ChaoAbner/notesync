package com.cvte.notesync.common.response;

import com.cvte.notesync.constant.NoteHttpCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果返回
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -4135707091666459359L;

    // 状态码
    private int code;

    // 信息
    private String msg;

    // 数据
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

    public static Result success(Object data) {
        return new Result(NoteHttpCode.SUCCESS_CODE, "success", data);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }
}
