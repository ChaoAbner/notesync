package com.cvte.notesync.controller;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.entity.VO;
import com.cvte.notesync.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    @ApiOperation("根据用户名查询用户")
    public VO findUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return VO.success(user);
    }

    @PostMapping("/{username}")
    @ApiOperation("插入一个新用户")
    public VO insertUser(@PathVariable String username) {
        userService.insertUser(username);
        return VO.success();
    }

    @DeleteMapping("/{username}")
    @ApiOperation("退出登录")
    public VO logout(@PathVariable String username) {
        userService.logout(username);
        return VO.success();
    }
}
