package com.cvte.notesync.service;


import com.cvte.notesync.entity.User;

public interface UserService {

    /**
     * 根据用户名查找用户
     */
    User findUserByUsername(String username);

    /**
     * 根据用户id查找用户
     */
    User findUserByUserId(int userId);

    /**
     * 插入用户
     */
    User insertUserByUsername(String username);

    /**
     * 删除用户
     */
    void deleteUserByUsername(String username);
}
