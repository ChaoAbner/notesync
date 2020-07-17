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

    @Autowired
    Audience audience;

    @Test
    public void parseJwt() {
        String token = JwtUtil.createJwt("1", "chaochao", audience);
        System.out.println(token);
        int userId = JwtUtil.getUserId(token, audience.getBase64Secret());
        System.out.println("userId:" + userId);
        String username = JwtUtil.getUsername(token, audience.getBase64Secret());
        System.out.println("username:" + username);
        boolean expiration = JwtUtil.isExpiration(token, audience.getBase64Secret());
        System.out.println("是否过期:" + expiration);
    }

    @Test
    public void createJwt() {
        String token = JwtUtil.createJwt("1", "chaochao", audience);
        System.out.println(token);
    }
}
