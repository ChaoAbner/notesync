package com.cvte.notesync.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VO implements Serializable {

    private int code;

    private String msg;

    private Object data;

    VO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    VO(int code, String msg) {
        this(code, msg, null);
    }

    public static VO success() {
        return new VO(0, "success");
    }

    public static VO success(String msg) {
        return new VO(0, msg);
    }

    public static VO success(Object data) {
        return new VO(0, "success", data);
    }

    public static VO error(int code, String msg) {
        return new VO(code, msg);
    }
}
