package com.cvte.notesync.mappertest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.utils.RedisKeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * dao层单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NoteMapperTest {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 插入一条笔记
     */
    @Test
    public void insertNote() {
        Note note = new Note();
        note.setStatus(1);
//        note.setVersion(1);
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
        Note note = noteMapper.selectById(26);
        System.out.println(note);
    }

    @Test
    public void selectByWrapper() {
        Note note = noteMapper.selectById(26);
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", note.getUserId()).eq("status", 3);
//        List<Note> notes = noteMapper.selectList(wrapper);
        Page<Note> notePage = new Page<Note>().setCurrent(0).setSize(2);
        Page<Note> notePage1 = noteMapper.selectPage(notePage, wrapper);
        System.out.println(notePage1.getRecords());
        System.out.println(notePage1.getTotal());
    }

    @Test
    public void selectBatchNoteByIds() {
        String userNoteskey = RedisKeyUtil.noteListKey(12);
        // 根据修改时间排序
        Set<ZSetOperations.TypedTuple<Object>> set =
                redisTemplate.opsForZSet().reverseRangeWithScores(userNoteskey, 0, -1);

        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
        ArrayList<Integer> ids = new ArrayList<>();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            Integer id = (Integer) next.getValue();
            ids.add(id);
        }
        List<Note> notes = noteMapper.selectBatchIds(ids);
        for (Note note : notes) {
            System.out.println(note);
        }

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
