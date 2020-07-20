package com.cvte.notesync.service;

import com.cvte.notesync.entity.Note;

public interface SyncService {

    Object isNeedSync(int version, int noteId);

    Note syncNoteToClient(int noteId);

    int syncNodeFromClient(Note note, long updateTimeStamp, int userId);

}
