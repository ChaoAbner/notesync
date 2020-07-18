package com.cvte.notesync.utilstest;


import com.cvte.notesync.entity.Audience;
import com.cvte.notesync.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Jwt测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTest {

    private String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEyLCJzdWIiOiJhYmNjIiwiaXNzIjoiMDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjYiLCJpYXQiOjE1OTUwMzY0MjUsImF1ZCI6InJlc3RhcGl1c2VyIiwiZXhwIjoxNTk1MDM3NjI1LCJuYmYiOjE1OTUwMzY0MjV9.-vSLQ6Vwhirg7ms1ifn8rEhX0nrmCmQFnJBgSZ1OPn4";

    @Autowired
    Audience audience;

    @Test
    public void parseJwt() {
//        System.out.println(token);
        int userId = JwtUtil.getUserId(testToken, audience.getBase64Secret());
        System.out.println("userId:" + userId);
        String username = JwtUtil.getUsername(testToken, audience.getBase64Secret());
        System.out.println("username:" + username);
        boolean expiration = JwtUtil.isExpiration(testToken, audience.getBase64Secret());
        System.out.println("是否过期:" + expiration);
    }

    @Test
    public void createJwt() {
        String token = JwtUtil.createJwt(12, "abcc", audience);
        System.out.println(token);
    }
}
