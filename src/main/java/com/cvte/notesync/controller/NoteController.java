package com.cvte.notesync.controller;

import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.NoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/user/{username}")
    @ApiOperation("通过用户名查找笔记列表")
    public Result findNotesByUsername(@PathVariable String username) {
        List<Note> notes = noteService.findNotesByUserName(username);
        return Result.success(notes);
    }

    @GetMapping("/{noteId}")
    @ApiOperation("通过笔记id查找笔记")
    public Result findNoteByNoteId(@PathVariable int noteId) {
        Note note = noteService.findNoteById(noteId);
        return Result.success(note);
    }

    @PostMapping("/detail/user/{username}")
    @ApiOperation("插入一条新的笔记")
    public Result insertNode(@PathVariable String username,
                             @RequestParam String title, @RequestParam String content) {
        Note note = noteService.insertNote(title, content, username);
        return Result.success(note);
    }

    @PutMapping("/{noteId}/user/{username}")
    @ApiOperation("根据笔记id更新笔记")
    public Result updateNote(@PathVariable int noteId, @PathVariable String username,
                             @RequestParam String title, @RequestParam String content) {
        noteService.updateNote(noteId, title, content, username);
        return Result.success();
    }

    @DeleteMapping("/{noteId}/user/{username}")
    @ApiOperation("根据笔记id删除笔记")
    public Result deleteNode(@PathVariable int noteId, @PathVariable String username) {
        noteService.deleteNode(noteId, username);
        return Result.success();
    }

}
