package com.cvte.notesync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public List<Note> findNotesByUserId(int userId, int start, int limit) {
        // 根据修改时间排序
        List<Note> notes = noteMapper.selectNotesByUserId(userId, start, limit);
        return notes;
    }

    @Override
    public Note findNoteById(int noteId) {
        Note note = noteMapper.selectById(noteId);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        Assert.isTrue(note.getStatus() != 2, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        return note;
    }

    @Override
    public List<Note> findDeletedNotesByUserId(int userId, int start, int limit) {
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        // 设置条件
        wrapper.eq("user_id", userId).eq("status", 2);
        // 设置分页
        Page<Note> page = new Page<Note>().setCurrent(start).setSize(limit);
        Page<Note> resultPage = noteMapper.selectPage(page, wrapper);
        return resultPage.getRecords();
    }

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

    @Override
    public Note updateNote(Note note, long updateTimeStamp, int userId) {
        // 更新时间
        note.setUpdateTime(new Date(updateTimeStamp));
        note.setUserId(userId);
        note.setStatus(note.getStatus());
        // 更新数据库
        int updateResult = noteMapper.updateById(note);
        Assert.isTrue(updateResult == 1, NoteHttpStatus.NOTE_UPDATE_FAIL.getErrMsg());
        return note;
    }

    @Override
    public void updateNoteStatus(int noteId, int status, int userId) {
        Note note = new Note();
        note.setId(noteId);
        note.setStatus(status);
        note.setUserId(userId);
        int updateResult = noteMapper.updateById(note);
        Assert.isTrue(updateResult == 1, NoteHttpStatus.NOTE_UPDATE_FAIL.getErrMsg());
    }

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
