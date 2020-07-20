package com.cvte.notesync.service;

import com.cvte.notesync.entity.Note;

public interface SyncService {

    Object isNeedSync(long localUpdateTime, int noteId);

    Note syncNoteToClient(int noteId);

    long syncNodeFromClient(Note note, long updateTimeStamp, int userId);

}
