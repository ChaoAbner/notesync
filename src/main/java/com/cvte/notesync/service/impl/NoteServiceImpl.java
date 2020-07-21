package com.cvte.notesync.service.impl;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.NoteService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查找笔记列表
     * @param username
     * @return
     */
    @Override
    public List<Note> findNotesByUserId(int userId, int start, int limit) {
        // 根据修改时间排序
        List<Note> notes = noteMapper.selectNotesByUserId(userId, start, limit);
        return notes;
    }

    /**
     * 根据id查找笔记
     * @param noteId
     * @return
     */
    @Override
    public Note findNoteById(int noteId) {
        Note note = noteMapper.selectById(noteId);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        Assert.isTrue(note.getStatus() != 2, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());

        return note;
    }

    /**
     * 插入笔记
     * @param title
     * @param content
     * @param username
     * @return
     */
    @Override
    public Note insertNote(Note note, long updateTimeStamp, int userId) {
        // 初始化属性
        note.init();
        note.setUpdateTime(new Date(updateTimeStamp));
        note.setUserId(userId);
        // 入库
        int insertResult = noteMapper.insert(note);
        Assert.isTrue(insertResult == 1, NoteHttpStatus.NOTE_INSERT_FAIL.getErrMsg());
        return note;
    }

    /**
     * 更新笔记
     * @param noteId
     * @param title
     * @param content
     * @param username
     * @return
     */
    @Override
    public Note updateNote(Note note, long updateTimeStamp, int userId) {
        // 更新时间
        note.setUpdateTime(new Date(updateTimeStamp));
        note.setUserId(userId);
        note.setStatus(1);
        // 更新数据库
        int updateResult = noteMapper.updateById(note);
        Assert.isTrue(updateResult == 1, NoteHttpStatus.NOTE_UPDATE_FAIL.getErrMsg());
        return note;
    }

    /**
     * 删除笔记
     * @param noteId
     * @param username
     */
    @Override
    public void deleteNode(int noteId, long updateTimeStamp, int userId) {
        // 设置status = 2
        Note note = new Note();
        note.setId(noteId);
        note.setStatus(2);
        note.setUserId(userId);
        note.setUpdateTime(new Date(updateTimeStamp));
        int deleteResult = noteMapper.updateById(note);
        Assert.isTrue(deleteResult == 1, NoteHttpStatus.NOTE_DELETE_FAIL.getErrMsg());
    }
}
