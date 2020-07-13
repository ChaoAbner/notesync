package com.cvte.notesync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {

        return userMapper.selectByUsername(username);
    }

    @Override
    public void insertUser(String username) {

    }

    @Override
    public void logout(String username) {

    }
}
