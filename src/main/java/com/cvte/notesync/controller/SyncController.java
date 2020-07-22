package com.cvte.notesync.controller;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.SyncService;
import com.cvte.notesync.utils.HolderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync")
@Api(tags = "同步接口")
public class SyncController {

    @Autowired
    SyncService syncService;

    @GetMapping("/note/{noteId}/need")
    @ApiOperation("判断是否需要同步笔记")
    public Result isNeedSync(@RequestParam(name = "timestamp") long localUpdateTime, @PathVariable int noteId) {
        Object needSync = syncService.isNeedSync(localUpdateTime, noteId);
        return Result.success(needSync);
    }

    @GetMapping("/note/{noteId}")
    @ApiOperation("向客户端同步笔记")
    public Result syncNoteToClient(@PathVariable int noteId) {
        Note note = syncService.syncNoteToClient(noteId);
        return Result.success(note);
    }

    @PutMapping("/note")
    @ApiOperation("向服务端同步笔记")
    public Result syncNoteFromClient(@RequestBody Note note, @RequestParam(name = "timestamp") long updateTimeStamp) {
        long updateTime = syncService.syncNodeFromClient(note, updateTimeStamp, HolderUtil.getUserId());
        JSONObject json = new JSONObject();
        json.put("updateTime", updateTime);
        return Result.success(json);
    }
}
