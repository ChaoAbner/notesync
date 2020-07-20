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

    @PostMapping("/timestamp/{updateTimestamp}")
    @ApiOperation("插入一条新的笔记")
    public  Result insertNode(@RequestBody Note note, @PathVariable long updateTimestamp) {
        Note resultNote = noteService.insertNote(note, updateTimestamp, HolderUtil.getUserId());
        return Result.success(resultNote);
    }

    @PutMapping("/timestamp/{updateTimestamp}")
    @ApiOperation("根据笔记id更新笔记")
    public Result updateNote(@RequestBody Note note, @PathVariable long updateTimestamp) {
        noteService.updateNote(note, updateTimestamp, HolderUtil.getUserId());
        return Result.success();
    }

    @DeleteMapping("/{noteId}/timestamp/{updateTimestamp}")
    @ApiOperation("根据笔记id删除笔记")
    public Result deleteNode(@PathVariable int noteId, @PathVariable long updateTimestamp) {
        noteService.deleteNode(noteId, updateTimestamp, HolderUtil.getUserId());
        return Result.success();
    }

}
