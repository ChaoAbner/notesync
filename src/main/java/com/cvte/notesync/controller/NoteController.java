package com.cvte.notesync.controller;

import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.utils.HolderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
@Api(tags = "笔记接口")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/user")
    @ApiOperation("查找用户的笔记列表")
    public Result findNotesByUserId(@RequestParam int start, @RequestParam int limit) {
        List<Note> notes = noteService.findNotesByUserId(HolderUtil.getUserId(), start, limit);
        return Result.success(notes);
    }

    @GetMapping("/{noteId}")
    @ApiOperation("通过笔记id查找笔记")
    public Result findNoteByNoteId(@PathVariable int noteId) {
        Note note = noteService.findNoteById(noteId);
        return Result.success(note);
    }

    @GetMapping("/basket")
    @ApiOperation("查找用户删除的笔记")
    public Result findDeletedNotesByUserId(@RequestParam int start, @RequestParam int limit) {
        List<Note> deleteNotes = noteService.findDeletedNotesByUserId(HolderUtil.getUserId(), start, limit);
        return Result.success(deleteNotes);
    }

    @PostMapping("/")
    @ApiOperation("插入一条新的笔记")
    public Result insertNode(@RequestBody Note note,
                             @RequestParam(name = "timestamp") long updateTimestamp) {
        Note resultNote = noteService.insertNote(note, updateTimestamp, HolderUtil.getUserId());
        return Result.success(resultNote);
    }

    @PutMapping("{noteId}/status/{status}")
    @ApiOperation("更新笔记的状态")
    public Result updateNoteStatus(@PathVariable int noteId, @PathVariable int status) {
        noteService.updateNoteStatus(noteId, status, HolderUtil.getUserId());
        return Result.success();
    }

    @PutMapping("/")
    @ApiOperation("更新一条笔记")
    public Result updateNote(@RequestBody Note note,
                             @RequestParam(name = "timestamp") long updateTimestamp) {
        noteService.updateNote(note, updateTimestamp, HolderUtil.getUserId());
        return Result.success();
    }

    @DeleteMapping("/{noteId}")
    @ApiOperation("根据笔记id删除笔记")
    public Result deleteNode(@PathVariable int noteId,
                             @RequestParam(name = "timestamp") long updateTimestamp) {
        noteService.deleteNode(noteId, updateTimestamp, HolderUtil.getUserId());
        return Result.success();
    }

}
