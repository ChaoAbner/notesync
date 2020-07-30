package com.cvte.notesync.service;

import com.cvte.notesync.entity.pojo.Audience;

import javax.servlet.http.HttpServletResponse;

public interface SessionService {

    /**
     * 创建并设置token
     */
    String createToken(HttpServletResponse response, int userId, String username, Audience audience);
}
