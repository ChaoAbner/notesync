package com.cvte.notesync.service.impl;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.NoteService;
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
        String userNoteskey = RedisKeyUtil.noteListKey(userId);
        // 根据修改时间排序
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(userNoteskey, start, limit);

        Assert.notNull(set, NoteHttpStatus.USER_NO_NOTES.getErrMsg());
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();

        ArrayList<Note> list = new ArrayList<>();
        // 装入笔记
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            Note note = (Note) next.getValue();
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
    public Note insertNote(Note note, long updateTimeStamp, int userId) {
        note.init();
        note.setUpdateTime(new Date(updateTimeStamp));
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
        // 保存笔记实体，以时间戳作为分数
        zSetOp.add(userNotesKey, note, note.getUpdateTime().getTime());
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
        // 更新数据库
        noteMapper.updateById(note);
        // 更新redis
        String key = RedisKeyUtil.noteKey(note.getId());
        Note redisNote = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(redisNote, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        String userNotesKey = RedisKeyUtil.noteListKey(userId);
        // 删除原来sorted set中的note
        redisTemplate.opsForZSet().remove(userNotesKey, redisNote);
        // 更新note版本，更新时间
        redisNote.setVersion(redisNote.getVersion() + 1);
        redisNote.setTitle(note.getTitle());
        redisNote.setUpdateTime(note.getUpdateTime());
        redisTemplate.opsForValue().set(key, redisNote);
        // 设置新的值和分数
        redisTemplate.opsForZSet().add(userNotesKey, redisNote, redisNote.getUpdateTime().getTime());
        return redisNote;
    }

    /**
     * 删除笔记
     * @param noteId
     * @param username
     */
    @Override
    public void deleteNode(int noteId, int updateTimeStamp, int userId) {
        String key = RedisKeyUtil.noteKey(noteId);
        // 设置status = 2
        Note redisNote = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(redisNote, NoteHttpStatus.NOTE_NOT_EXIST.getErrMsg());
        redisNote.setStatus(2);
        redisNote.setUpdateTime(new Date(updateTimeStamp));
        redisTemplate.opsForValue().set(key, redisNote);
    }
}
