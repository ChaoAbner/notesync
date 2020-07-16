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
//        Audience audience = (Audience) SpringContextUtil.getBean("audience");
//        System.out.println(audience);
        Date date = new Date();
        long time = date.getTime();
        String s = String.valueOf(time);
        String substring = s.substring(0, 6);
        String substring1 = s.substring(6);
        String r = substring + "." + substring1;
        Double aDouble = new Double(r);
        System.out.println(aDouble);
    }
}
