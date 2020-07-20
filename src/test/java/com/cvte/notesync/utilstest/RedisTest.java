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

import java.math.BigDecimal;
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

        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("value:"+next.getValue()+" score:"+next.getScore());
        }
    }

    @Test
    public void updateScore() {
        String userNotesKey = RedisKeyUtil.noteListKey(13);
        redisTemplate.opsForZSet().add(userNotesKey, 60,
                DateTimeUtil.dateToScore(new Date()));
    }

    @Test
    public void sortedSetTest() {
        ZSetOperations zsp = redisTemplate.opsForZSet();

        Note note = new Note();
        note.setId(1);
        note.setTitle("标题");
        note.setContent("内容");
        String key = "setkey111";

        zsp.add(key, note, 2);
        System.out.println(zsp.rank(key, note));

        System.out.println("读取存入的note对象：");
        Set x = zsp.rangeByScore(key, 0, 100);
        System.out.println(x);
    }

    @Test
    public void timeMiss() throws InterruptedException {
        ZSetOperations zsp = redisTemplate.opsForZSet();
        String key = "test:score:timemiss2";
//        IntStream.of(10).forEach(i -> new Thread(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                Date date = new Date();
//                long time = date.getTime();
//                System.out.println(time);
//                Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
//                zsp.add(key, i, time);
//            }
//        }));

//
        Set set = zsp.rangeWithScores(key, 0, -1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();

        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("value:"+next.getValue() +
                    " score:"+ DateTimeUtil.ScoreToDate(next.getScore()));
        }
    }

    @Test
    public void bigDecimal() {
        double i = 1.595215830138E12;
        BigDecimal bigDecimal = new BigDecimal(i);
        String s = bigDecimal.toPlainString();
        System.out.println(s);
        Long time = Long.valueOf(s);
        Date date = new Date(time);
        System.out.printf(date.toString());
    }
}
