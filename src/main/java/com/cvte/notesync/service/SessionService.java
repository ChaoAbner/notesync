package com.cvte.notesync.service;

import com.cvte.notesync.entity.Audience;

import javax.servlet.http.HttpServletResponse;

public interface SessionService {

    String createToken(HttpServletResponse response, int userId, String username, Audience audience);
}
