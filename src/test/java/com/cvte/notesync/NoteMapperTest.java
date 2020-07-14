package com.cvte.notesync;


import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoteMapperTest {

    @Autowired
    NoteMapper noteMapper;

    @Test
    public void insertNote() {
        Note note = new Note();
        note.setStatus(1);
        note.setVersion(1);
        note.setTitle("哈哈");
        note.setContent("嘿嘿嘿");
        note.setCreateTime(new Date());
        note.setUpdateTime(new Date());

        noteMapper.insert(note);
    }

    @Test
    public void selectNote() {
        Note note = noteMapper.selectById(1);
        System.out.println(note);
    }
}
