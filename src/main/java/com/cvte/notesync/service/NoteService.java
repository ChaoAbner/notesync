package com.cvte.notesync.service;


import com.cvte.notesync.entity.Note;

import java.util.List;

public interface NoteService {

    /**
     * 查找用户的笔记列表
     * @param userId
     * @param start
     * @param limit
     */
    List<Note> findNotesByUserId(int userId, int start, int limit);

    /**
     * 根据笔记id查找笔记
     * @param noteId
     */
    Note findNoteById(int noteId);

    /**
     * 查找用户删除的笔记
     * @param userId
     * @param start
     * @param limit
     */
    List<Note> findDeletedNotesByUserId(int userId, int start, int limit);

    /**
     * 插入一个笔记
     * @param note
     * @param updateTimeStamp
     * @param userId
     */
    Note insertNote(Note note, long updateTimeStamp, int userId);

    /**
     * 更新笔记
     * @param note
     * @param updateTimeStamp
     * @param userId
     */
    Note updateNote(Note note, long updateTimeStamp, int userId);

    /**
     * 更新笔记的状态
     * @param noteId
     * @param status
     * @param userId
     */
    void updateNoteStatus(int noteId, int status, int userId);

    /**
     * 删除笔记
     * @param noteId
     * @param updateTimeStamp
     * @param userId
     */
    void deleteNode(int noteId, long updateTimeStamp, int userId);
}
