package com.cvte.notesync;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertUser() {
        User user = new User();

        user.setRegisterTime(new Date());
        user.setUsername("abd");
        for (int i = 0; i < 10; i++) {
            userMapper.insert(user);
        }
    }

    @Test
    void selectUsers() {
        List<User> users = userMapper.selectList(null);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void selectUserByUsername() {
        User abd = userMapper.selectByUsername("abd");
        System.out.println(abd);
    }

    @Test
    void deleteUser() {
    }
}
