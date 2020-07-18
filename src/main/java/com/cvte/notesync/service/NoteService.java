package com.cvte.notesync.service;


import com.cvte.notesync.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findNotesByUserId(int userId, int start, int limit);

    Note findNoteById(int noteId);

    Note insertNote(String title, String content, int userId);

    Note updateNote(int noteId, String title, String content, int userId);

    void deleteNode(int noteId, int userId);
}
