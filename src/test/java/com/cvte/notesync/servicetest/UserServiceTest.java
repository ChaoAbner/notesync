package com.cvte.notesync.servicetest;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void findUserByUsername() {
        User user = userService.findUserByUsername("用户2");
        System.out.println(user);
    }

    @Test
    public void insertUserByUsername() {
        User user = userService.insertUserByUsername("abcc");
        System.out.println(user);
    }

    @Test
    public void deleteUserByUsername() {
        userService.deleteUserByUsername("用户3");
    }

}
