package com.cvte.notesync.service;

import com.cvte.notesync.entity.Note;

public interface SyncService {

    Object isNeedSync(int version, int noteId);

    Note syncNoteToClient(int noteId);

    int syncNodeFromClient(int nodeId, int userId, String title, String content);

}
