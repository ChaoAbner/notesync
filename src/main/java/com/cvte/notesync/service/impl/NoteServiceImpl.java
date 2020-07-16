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

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Note> findNotesByUserName(String username) {
        User user = userMapper.selectByUsername(username);

        String key = RedisKeyUtil.noteListKey(user.getId());
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
        if (set == null) {
            throw new NoteException(NoteHttpStatus.USER_NO_NOTES);
        }
        ArrayList<Note> list = new ArrayList<>();
        // 装入笔记
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            int noteId = (Integer) next.getValue();
            Note note = findNoteById(noteId);
            if (note != null)
                list.add(note);
        }

        return list;
    }

    @Override
    public Note findNoteById(int noteId) {
        String key = RedisKeyUtil.noteKey(noteId);
        Note note = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, "笔记不存在");
        if (note.getStatus() == 2) {
//            throw new NoteException(NoteHttpStatus.NOTE_NOT_EXIST);
            return null;
        }
        // 聚合笔记内容
        String content = noteMapper.selectById(note.getId()).getContent();
        note.setContent(content);
        return note;
    }

    @Override
    public Note insertNote(String title, String content, String username) {
        // TODO 校验笔记是否所属当前用户
        User user = userMapper.selectByUsername(username);

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        // 入库
        noteMapper.insert(note);

        // 存入redis, 存note笔记实体
        String noteKey = RedisKeyUtil.noteKey(note.getId());
        redisTemplate.opsForValue().set(noteKey, note.clearContent());
        // 保存用户和笔记的映射
        String userNotesKey = RedisKeyUtil.noteListKey(user.getId());
        ZSetOperations zSetOp = redisTemplate.opsForZSet();
        // 笔记列表不为空并且笔记不能重复添加
        if (zSetOp.size(userNotesKey) != 0 && zSetOp.rank(userNotesKey, note.getId()) != null) {
            throw new NoteException(NoteHttpStatus.ILLEGAL_OPERATION);
        }
        zSetOp.add(userNotesKey, note.getId(), DateTimeUtil.dateToScore(note.getUpdateTime()));

        return note;
    }

    @Override
    public Note updateNote(int noteId, String title, String content, String username) {
        // TODO 校验笔记是否所属当前用户
        Note note = noteMapper.selectById(noteId);
        note.setTitle(title);
        note.setContent(content);
        // 更新数据库
        noteMapper.updateById(note);
        // 更新redis
        String key = RedisKeyUtil.noteKey(noteId);
        Note rNote = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(rNote, "笔记不存在");
        // 更新版本，时间
        rNote.setVersion(rNote.getVersion() + 1);
        rNote.setTitle(title);
        rNote.setUpdateTime(new Date());
        redisTemplate.opsForValue().set(key, rNote);
        // 更新分数
        User user = userMapper.selectByUsername(username);
        String userNotesKey = RedisKeyUtil.noteListKey(user.getId());
        redisTemplate.opsForZSet().add(userNotesKey, note.getId(),
                DateTimeUtil.dateToScore(rNote.getUpdateTime()));
        return rNote;
    }

    @Override
    public void deleteNode(int noteId, String username) {
        // TODO 校验笔记是否所属当前用户

        String key = RedisKeyUtil.noteKey(noteId);
        // 设置status = 2
        Note note = (Note) redisTemplate.opsForValue().get(key);
        Assert.notNull(note, "笔记不存在");
        note.setStatus(2);
        note.setUpdateTime(new Date());
        redisTemplate.opsForValue().set(key, note);
    }
}
