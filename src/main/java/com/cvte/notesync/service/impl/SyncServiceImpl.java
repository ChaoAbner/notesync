package com.cvte.notesync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.service.SyncService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;

@SuppressWarnings("ALL")
@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    NoteService noteService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 判断是否同步
     * @param version
     * @param noteId
     * @return
     */
    @Override
    public Object isNeedSync(long localUpdateTime, int noteId) {
        Note note = noteService.findNoteById(noteId);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        JSONObject jo = new JSONObject();
        jo.put("need", 0);
        // 服务器note更新时间后于本地更新时间,说明需要同步
        if (note.getUpdateTime().after(new Date(localUpdateTime))) {
            jo.put("need", 1);
        }
        return jo;
    }

    /**
     * 同步笔记到客户端
     * @param noteId
     * @return
     */
    @Override
    public Note syncNoteToClient(int noteId) {
        return noteService.findNoteById(noteId);
    }

    /**
     * 更新服务端的笔记
     * @param noteId
     * @param title
     * @param content
     * @return version
     */
    @Override
    public long syncNodeFromClient(Note note, long updateTimeStamp, int userId) {
        // 更新MySQL
        Note resultNote = noteService.updateNote(note, updateTimeStamp, userId);
        return resultNote.getUpdateTime().getTime();
    }
}
