package com.cvte.notesync.mappertest;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 插入用户
     */
    @Test
    void insertUser() {
        User user = new User();

        user.setRegisterTime(new Date());
        user.setUsername("abd");
        int i = userMapper.insert(user);
        Assert.assertEquals(1L, i);
    }

    /**
     * 根据id查找用户
     */
    @Test
    void selectUserById() {
        User user = userMapper.selectById(3L);
        System.out.println(user);
    }

    /**
     * 根据username查找用户
     */
    @Test
    void selectUserByUsername() {
        User abd = userMapper.selectByUsername("nnnn");
        System.out.println(abd);
    }

    /**
     * 根据id删除用户
     */
    @Test
    void deleteUserById() {
        int i = userMapper.deleteById(3);
        Assert.assertEquals(1L, i);
    }
}
