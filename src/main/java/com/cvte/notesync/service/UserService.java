package com.cvte.notesync.service;


import com.cvte.notesync.entity.User;

public interface UserService {

    User findUserByUsername(String username);

    User findUserByUserId(int userId);

    User insertUserByUsername(String username);

    void deleteUserByUsername(String username);
}
