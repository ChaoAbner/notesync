package com.cvte.notesync.common.exception;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import lombok.Data;

@Data
public class NoteException extends RuntimeException{

    private int code;

    private String msg;

    public NoteException(NoteHttpStatus status) {
        super(status.toString());

        this.code = status.getErrCode();
        this.msg = status.getErrMsg();
    }
}
