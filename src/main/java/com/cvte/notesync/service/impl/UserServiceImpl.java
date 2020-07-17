package com.cvte.notesync.service.impl;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.mapper.UserMapper;
import com.cvte.notesync.service.UserService;
import com.cvte.notesync.utils.RedisKeyUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查找用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 插入用户
     * @param username
     * @return
     */
    @Override
    public User insertUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setRegisterTime(new Date());
        // 插入数据库
        userMapper.insert(user);
        // 调用redis服务存储user二进制
        String key = RedisKeyUtil.userKey(user.getId());
        redisTemplate.opsForValue().set(key, user);
        return user;
    }

    /**
     * 删除用户
     * @param username
     */
    @Override
    public void deleteUserByUsername(String username) {
        // 查询user
        User user = findUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        String key = RedisKeyUtil.userKey(user.getId());
        // 删除对应user
        redisTemplate.delete(key);
        userMapper.deleteById(user.getId());
    }
}
