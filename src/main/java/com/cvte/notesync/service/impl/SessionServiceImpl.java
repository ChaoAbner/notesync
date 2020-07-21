package com.cvte.notesync.service.impl;


import com.cvte.notesync.entity.Audience;
import com.cvte.notesync.service.SessionService;
import com.cvte.notesync.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public String createToken(HttpServletResponse response, int userId, String username, Audience audience) {
        String token = JwtUtil.createJwt(userId, username, audience);
        // 设置到响应头
        response.setHeader(JwtUtil.AUTH_HEADER_KEY, token);
        return token;
    }
}
