package com.cvte.notesync.controller;

import com.cvte.notesync.entity.Note;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.service.SyncService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    SyncService syncService;

    @GetMapping("/{version}/note/{noteId}")
    @ApiOperation("判断是否需要同步笔记")
    public Result isNeedSync(@PathVariable int version, @PathVariable int noteId) {
        Object needSync = syncService.isNeedSync(version, noteId);

        return Result.success(needSync);
    }

    @GetMapping("/note/{noteId}")
    @ApiOperation("向客户端同步笔记")
    public Result syncNoteToClient(@PathVariable int noteId) {
        Note note = syncService.syncNoteToClient(noteId);
        return Result.success(note);
    }

    @PutMapping("/note/{noteId}")
    @ApiOperation("向服务端同步笔记")
    public Result syncNoteFromClient(@PathVariable int noteId, String title, String content) {
        syncService.syncNodeFromClient(noteId, title, content);
        return Result.success();
    }
}
