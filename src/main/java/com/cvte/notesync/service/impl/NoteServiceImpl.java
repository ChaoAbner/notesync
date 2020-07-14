package com.cvte.notesync.service.impl;

import com.cvte.notesync.entity.Note;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        Set<Object> set = redisTemplate.opsForZSet().range(key, 0, -1);

        assert set != null;
        ArrayList<Note> list = new ArrayList<>();
        for (Object o : set) {
            int noteId = (int) o;
            String noteKey = RedisKeyUtil.noteKey(noteId);
            Note note = (Note) redisTemplate.opsForValue().get(noteKey);
            list.add(note);
        }
        return list;
    }

    @Override
    public Note findNoteById(int noteId) {
        String key = RedisKeyUtil.noteKey(noteId);
        return (Note) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void insertNote(String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUpdateTime(new Date());
        note.setCreateTime(new Date());
        // 入库
        noteMapper.insert(note);

        // 存入redis
        String key = RedisKeyUtil.noteKey(note.getId());
        redisTemplate.opsForValue().set(key, note.clearContent());
    }

    @Override
    public void updateNote(int noteId, String title, String content) {
        Note note = noteMapper.selectById(noteId);
        note.setTitle(title);
        note.setTitle(content);
        // 更新数据库
        noteMapper.update(note, null);
        // 更新redis
        String key = RedisKeyUtil.noteKey(noteId);
        Note rNote = (Note) redisTemplate.opsForValue().get(key);
        assert rNote != null;
        rNote.setVersion(rNote.getVersion() + 1);
        rNote.setTitle(title);
        redisTemplate.opsForValue().set(key, rNote);
    }

    @Override
    public void deleteNode(int noteId, String username) {

    }
}
