package com.cvte.notesync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.service.SyncService;
import com.cvte.notesync.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        assert note != null;

        JSONObject jo = new JSONObject();
        jo.put("need", 0);
        jo.put("version", version);
        if (version != note.getVersion()) {
            jo.put("version", version + 1);
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
        return noteMapper.selectById(noteId);
    }

    /**
     * 更新服务端的笔记
     * @param noteId
     * @param title
     * @param content
     */
    @Override
    public void syncNodeFromClient(int noteId, String title, String content) {
        // 更新MySQL
        noteService.updateNote(noteId, title, content);
    }
}
