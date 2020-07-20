package com.cvte.notesync;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

    @Test
    public void test() {
        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);
    }
}
