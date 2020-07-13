package com.cvte.notesync.service;

import com.cvte.notesync.entity.Note;

public interface SyncService {

    Object isNeedSync(int version, int noteId);

    Note syncNoteToClient(int noteId);

    void syncNodeFromClient(int nodeId, String title, String content);

}
