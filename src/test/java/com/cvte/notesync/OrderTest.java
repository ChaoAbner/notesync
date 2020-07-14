package com.cvte.notesync;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

    @Test
    public void test() {
        String a = "Bearer-" + "fsjhvkjsd";
        System.out.println(a.substring(7));
    }
}
