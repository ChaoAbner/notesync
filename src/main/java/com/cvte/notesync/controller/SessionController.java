package com.cvte.notesync.controller;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.Audience;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.service.UserService;
import com.cvte.notesync.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    Audience audience;

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param response
     * @param username
     * @return
     */
    @PostMapping("/{username}")
    @ApiOperation("用户登录")
    public Result login(HttpServletResponse response, @PathVariable String username) {
        User user = userService.findUserByUsername(username);
        // 不存在则创建用户
        if (user == null) {
            user = userService.insertUserByUsername(username);
        }
        String token = JwtUtil.createJwt(String.valueOf(user.getId()), username, audience);
//        token = JwtUtil.TOKEN_PREFIX + token;
        // 设置到响应头
        response.setHeader(JwtUtil.AUTH_HEADER_KEY, token);
        // 返回给客户端
        JSONObject jo = new JSONObject();
        jo.put("token", token);
        jo.put("user", user);
        return Result.success(jo);
    }

    @DeleteMapping("/{token}")
    public Result logout(HttpServletResponse response, @PathVariable String token) {
        return Result.success();
    }
}
