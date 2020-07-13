package com.cvte.notesync.service.impl;

import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Note> findNotesByUserName(String username) {
        return null;
    }

    @Override
    public Note findNoteById(int noteId) {
        return null;
    }

    @Override
    public void insertNote(String title, String content) {

    }

    @Override
    public void updateNote(int noteId, String title, String content) {

    }

    @Override
    public void deleteNode(int noteId, String username) {

    }
}
