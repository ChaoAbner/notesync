package com.cvte.notesync;


import com.cvte.notesync.entity.Audience;
import com.cvte.notesync.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

    @Test
    public void test() {
        Audience audience = (Audience) SpringContextUtil.getBean("audience");
        System.out.println(audience);
    }
}
