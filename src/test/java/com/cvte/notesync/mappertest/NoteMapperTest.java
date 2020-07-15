package com.cvte.notesync.mappertest;


import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * dao层单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NoteMapperTest {

    @Autowired
    NoteMapper noteMapper;

    /**
     * 插入一条笔记
     */
    @Test
    public void insertNote() {
        Note note = new Note();
        note.setStatus(1);
        note.setVersion(1);
        note.setTitle("士大夫大幅度");
        note.setContent("分隔符VS地方");
        note.setCreateTime(new Date());
        note.setUpdateTime(new Date());

        int i = noteMapper.insert(note);
        Assert.assertEquals(1L, i);
    }

    /**
     * 根据noteId查询笔记
     */
    @Test
    public void selectNoteById() {
        Note note = noteMapper.selectById(1);
        System.out.println(note);
    }

    /**
     * 更新笔记
     */
    @Test
    public void updateNote() {
        Note note = noteMapper.selectById(1);
        note.setContent("新内容1");
        note.setTitle("新标题1");
        int i = noteMapper.updateById(note);
        Assert.assertEquals(1L, i);
    }

    /**
     * 删除笔记
     */
    @Test
    public void deleteNote() {
        int noteId = 1;
        int i = noteMapper.deleteById(noteId);
        Assert.assertEquals(1L, i);
    }
}
