package com.cvte.notesync.service;


import com.cvte.notesync.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findNotesByUserName(String username);

    Note findNoteById(int noteId);

    void insertNote(String title, String content, String username);

    Note updateNote(int noteId, String title, String content, String username);

    void deleteNode(int noteId, String username);
}
