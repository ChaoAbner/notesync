package com.cvte.notesync.utilstest;


import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.NoteService;
import com.cvte.notesync.utils.DateTimeUtil;
import com.cvte.notesync.utils.RedisKeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    NoteService noteService;

    @Test
    public void redis() {
        String key = RedisKeyUtil.noteListKey(13);
        ZSetOperations zsp = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> set = zsp.reverseRangeWithScores(key, 0, -1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
        ArrayList<Note> list = new ArrayList<>();

        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("value:"+next.getValue()+" score:"+next.getScore());
//            ZSetOperations.TypedTuple<Object> next = iterator.next();
//            Integer noteId = (Integer) next.getValue();
//            Note note = noteService.findNoteById(noteId);
//            if (note != null)
//                list.add(note);
        }
    }

    @Test
    public void updateScore() {
        String userNotesKey = RedisKeyUtil.noteListKey(13);
        redisTemplate.opsForZSet().add(userNotesKey, 60,
                DateTimeUtil.dateToScore(new Date()));
    }
}
