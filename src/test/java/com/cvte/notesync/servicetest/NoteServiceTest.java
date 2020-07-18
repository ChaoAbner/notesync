package com.cvte.notesync.servicetest;


import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.NoteService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

    @Autowired
    NoteService noteService;

    private Note note;

    private Random random = new Random(System.currentTimeMillis());

    @Before
    public void before() {
        note = new Note();
        note.setTitle("这是标题" + random.nextInt(20));
        note.setContent("这是内容" + random.nextInt(20));
    }

    @After
    public void after() {
    }

    /**
     * 插入一条笔记
     */
    @Test
    public void insertNote() {
        for (int i = 0; i < 10; i++) {
            noteService.insertNote(note.getTitle(), note.getContent(), 12);
        }
    }

    /**
     * 通过noteId查找笔记
     */
    @Test
    public void selectNoteById() {
        Note note = noteService.findNoteById(59);
        System.out.println(note.getUpdateTime().getTime());
    }

    /**
     * 通过username查找用户的笔记列表
     */
    @Test
    public void selectNotesByUsername() {
        List<Note> notes = noteService.findNotesByUserId(12, 0, -1);
        for (Note note : notes) {
            System.out.println(note);
        }
    }

    /**
     * 更新笔记内容
     */
    @Test
    public void updateNote() {
        Note note = noteService.updateNote(5, "新的标题2", "新的内容2", 12);
        System.out.println(note);
    }

    /**
     * 根据noteId删除笔记
     */
    @Test
    public void deleteNoteById() {
        noteService.deleteNode(5, 12);
    }
}
