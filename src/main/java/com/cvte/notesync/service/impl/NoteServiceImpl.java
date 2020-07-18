package com.cvte.notesync.service.impl;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.utils.DateTimeUtil;
import com.cvte.notesync.utils.RedisKeyUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

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
        String key = RedisKeyUtil.noteListKey(userId);
        // 根据修改时间排序
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, start, limit);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
        Assert.notNull(set, NoteHttpStatus.USER_NO_NOTES.getErrMsg());

        ArrayList<Note> list = new ArrayList<>();
        // 装入笔记
        // TODO 循环数据库
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            int noteId = (Integer) next.getValue();
            Note note = findNoteById(noteId);
            if (note != null)
                list.add(note);
        }
        return list;
    }

    /**
     * 根据id查找笔记
     * @param noteId
     * @return
     */
    @Override
    public Note findNoteById(int noteId) {
        String key = RedisKeyUtil.noteKey(noteId);
        Note note = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        if (note.getStatus() == 2) {
            return null;
        }
        // 聚合笔记内容
        String content = noteMapper.selectById(note.getId()).getContent();
        note.setContent(content);
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
    public Note insertNote(String title, String content, int userId) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        // 入库
        noteMapper.insert(note);
        // 存入redis, 存note笔记实体
        String noteKey = RedisKeyUtil.noteKey(note.getId());
        redisTemplate.opsForValue().set(noteKey, note.clearContent());
        // 保存用户和笔记的映射
        String userNotesKey = RedisKeyUtil.noteListKey(userId);
        ZSetOperations zSetOp = redisTemplate.opsForZSet();
        // 笔记列表不为空并且笔记不能重复添加
        if (zSetOp.size(userNotesKey) != 0 && zSetOp.rank(userNotesKey, note.getId()) != null) {
            throw new NoteException(NoteHttpStatus.ILLEGAL_OPERATION);
        }
        zSetOp.add(userNotesKey, note.getId(), DateTimeUtil.dateToScore(note.getUpdateTime()));

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
    public Note updateNote(int noteId, String title, String content, int userId) {
        Note note = new Note();
        note.setId(noteId);
        note.setTitle(title);
        note.setContent(content);
        // 更新数据库
        noteMapper.updateById(note);
        // 更新redis
        String key = RedisKeyUtil.noteKey(noteId);
        Note rNote = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        // 更新版本，时间
        rNote.setVersion(rNote.getVersion() + 1);
        rNote.setTitle(title);
        rNote.setUpdateTime(new Date());
        redisTemplate.opsForValue().set(key, rNote);
        // 更新分数
        User user = userMapper.selectById(userId);
        String userNotesKey = RedisKeyUtil.noteListKey(user.getId());
        redisTemplate.opsForZSet().add(userNotesKey, note.getId(),
                DateTimeUtil.dateToScore(rNote.getUpdateTime()));
        return rNote;
    }

    /**
     * 删除笔记
     * @param noteId
     * @param username
     */
    @Override
    public void deleteNode(int noteId, int userId) {
        String key = RedisKeyUtil.noteKey(noteId);
        // 设置status = 2
        Note note = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        note.setStatus(2);
        note.setUpdateTime(new Date());
        redisTemplate.opsForValue().set(key, note);
    }
}
