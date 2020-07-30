package com.cvte.notesync.utils;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.constant.ValidValue;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("ALL")
@Component
public class CheckValidUtil {

    @Autowired
    NoteMapper noteMapper;

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

    /**
     * 检查笔记是否存在并且合法
     */
    public void checkNoteExistAndValid(int noteId) {
        Note note = noteMapper.selectById(noteId);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        Assert.isTrue(note.getStatus() == 1, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
    }
}
