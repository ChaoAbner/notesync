package com.cvte.notesync.service.impl;

import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User findUserByUserId(int userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User insertUserByUsername(String username) {
        User user = new User();
        user.init();
        user.setUsername(username);
        // 插入数据库
        userMapper.insert(user);
        return user;
    }

    @Override
    public void deleteUserByUsername(String username) {
        // 查询user
        User user = findUserByUsername(username);
        Assert.notNull(user, NoteHttpStatus.USER_NOT_EXIST.getErrMsg());
        if (user.getStatus() == 2) {
            throw new NoteException(NoteHttpStatus.USER_NOT_EXIST);
        }
        user.setStatus(2);
        userMapper.updateById(user);
    }
}
