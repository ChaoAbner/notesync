package com.cvte.notesync.service;


import com.cvte.notesync.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findNotesByUserId(int userId, int start, int limit);

    Note findNoteById(int noteId);

    Note insertNote(Note note, long updateTimeStamp, int userId);

    Note updateNote(Note note, long updateTimeStamp, int userId);

    void deleteNode(int noteId, long updateTimeStamp, int userId);
}
