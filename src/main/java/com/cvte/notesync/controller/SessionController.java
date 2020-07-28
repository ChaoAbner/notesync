package com.cvte.notesync.controller;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.annotation.ValidParameter;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.pojo.Audience;
import com.cvte.notesync.entity.User;
import com.cvte.notesync.service.SessionService;
import com.cvte.notesync.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/session")
@Api(tags = "会话接口")
public class SessionController {

    @Autowired
    Audience audience;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    /**
     * 登录
     * @param response
     * @param username
     * @return
     */
    @PostMapping("/{username}")
    @ApiOperation("用户登录")
    @ValidParameter
    public Result login(HttpServletResponse response, @PathVariable String username) {
        User user = userService.findUserByUsername(username);
        // 不存在则创建用户
        if (user == null) {
            user = userService.insertUserByUsername(username);
        }
        String token = sessionService.createToken(response, user.getId(), username, audience);
        // 返回给客户端
        JSONObject jo = new JSONObject();
        jo.put("token", token);
        jo.put("user", user);
        return Result.success(jo);
    }
}
