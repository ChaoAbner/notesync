package com.cvte.notesync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.service.SyncService;
import com.cvte.notesync.utils.RedisKeyUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    public Object isNeedSync(int version, int noteId) {
        String key = RedisKeyUtil.noteKey(noteId);
        Note note  = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        JSONObject jo = new JSONObject();
        jo.put("need", 0);
        if (version != note.getVersion()) {
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
    public int syncNodeFromClient(int noteId, int userId, String title, String content) {
        // TODO 修改参数 username => userId
        // 更新MySQL
        Note note = noteService.updateNote(noteId, title, content, userId);
        return note.getVersion();
    }
}
