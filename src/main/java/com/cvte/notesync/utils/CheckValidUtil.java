package com.cvte.notesync.utils;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.constant.ValidValue;
import com.cvte.notesync.entity.Note;
import io.jsonwebtoken.lang.Assert;

@SuppressWarnings("ALL")
public class CheckValidUtil {

    /**
     * 检查笔记的content和title长度
     * @param note
     */
    public static void checkNote(Object[] args) {
        Note note = null;
        for (Object arg : args) {
            if (arg instanceof Note) {
                note = (Note) arg;
                break;
            }
        }
        Assert.notNull(note, NoteHttpStatus.PARAMETER_ERROR.getErrMsg());
        String content = note.getContent();
        String title = note.getTitle();

        if (content.length() > ValidValue.NOTE_CONTENT_MAX_LENGTH ||
            title.length() > ValidValue.NOTE_TITLE_MAX_LENGTH) {
            throw new NoteException(NoteHttpStatus.PARAMETER_ERROR);
        }
    }

    /**
     * 检查用户名的长度
     * @param username
     */
    public static void checkUser(Object[] args) {
        String username = null;
        for (Object arg : args) {
            if (arg instanceof String) {
                username = (String) arg;
                break;
            }
        }
        Assert.notNull(username, NoteHttpStatus.PARAMETER_ERROR.getErrMsg());
        if (username.length() > ValidValue.USERNAME_MAX_LENGTH) {
            throw new NoteException(NoteHttpStatus.PARAMETER_ERROR);
        }
    }
}
