package com.cvte.notesync.service.impl;

import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.SyncService;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

    @Override
    public Object isNeedSync(int version, int noteId) {
        return null;
    }

    @Override
    public Note syncNoteToClient(int noteId) {
        return null;
    }

    @Override
    public void syncNodeFromClient(int nodeId, String title, String content) {

    }
}
