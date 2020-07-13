package com.cvte.notesync.service;


import com.cvte.notesync.entity.User;

public interface UserService {

    User findUserByUsername(String username);

    void insertUser(String username);

    void logout(String username);
}
