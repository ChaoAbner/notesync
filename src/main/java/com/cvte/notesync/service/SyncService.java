package com.cvte.notesync.service;

import com.cvte.notesync.entity.Note;

public interface SyncService {

    /**
     * 判断是否需要同步
     */
    Object isNeedSync(long localUpdateTime, int noteId);

    /**
     * 同步笔记到客户端
     */
    Note syncNoteToClient(int noteId);

    /**
     * 向服务端同步笔记
     */
    long syncNodeFromClient(Note note, long updateTimeStamp, int userId);

}
