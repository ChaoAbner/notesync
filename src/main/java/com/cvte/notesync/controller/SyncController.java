package com.cvte.notesync.controller;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.SyncService;
import com.cvte.notesync.utils.HolderUtil;
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
    public Result syncNoteFromClient(@PathVariable int noteId,
                                     @RequestParam String title, @RequestParam String content) {
        int version = syncService.syncNodeFromClient(noteId, HolderUtil.getUserId(), title, content);
        JSONObject json = new JSONObject();
        json.put("version", version);
        return Result.success(json);
    }
}
