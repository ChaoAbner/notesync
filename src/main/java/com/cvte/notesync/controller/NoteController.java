package com.cvte.notesync.controller;

import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.utils.HolderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/user")
    @ApiOperation("通过用户Id查找笔记列表")
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

    @PostMapping("/")
    @ApiOperation("插入一条新的笔记")
    public Result insertNode(@RequestParam String title, @RequestParam String content) {
        Note note = noteService.insertNote(title, content, HolderUtil.getUserId());
        return Result.success(note);
    }

    @PutMapping("/{noteId}")
    @ApiOperation("根据笔记id更新笔记")
    public Result updateNote(@PathVariable int noteId,
                             @RequestParam String title, @RequestParam String content) {
        noteService.updateNote(noteId, title, content, HolderUtil.getUserId());
        return Result.success();
    }

    @DeleteMapping("/{noteId}")
    @ApiOperation("根据笔记id删除笔记")
    public Result deleteNode(@PathVariable int noteId) {
        noteService.deleteNode(noteId, HolderUtil.getUserId());
        return Result.success();
    }

}
