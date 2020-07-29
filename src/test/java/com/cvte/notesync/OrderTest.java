package com.cvte.notesync;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.HtmlUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

    @Value("${file.domain}")
    String domain;

    @Test
    public void test() {
        String s = HtmlUtils.htmlEscape("<p>sdfdf123j2h3j2哈哈哈哈哈<br></p>");
        System.out.println(s);
        String s1 = HtmlUtils.htmlUnescape(s);
        System.out.println(s1);
    }

    @Test
    public void idxTest() {
        System.out.printf(domain);
    }
}
