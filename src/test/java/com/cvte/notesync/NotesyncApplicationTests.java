package com.cvte.notesync;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
class NotesyncApplicationTests {

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

}
