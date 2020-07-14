package com.cvte.notesync.controller;

import com.cvte.notesync.entity.User;
import com.cvte.notesync.common.response.Result;
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
    public Result findUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return Result.success(user);
    }

    @PostMapping("/{username}")
    @ApiOperation("插入一个新用户")
    public Result insertUser(@PathVariable String username) {
        userService.insertUserByUsername(username);
        return Result.success();
    }

    @DeleteMapping("/{username}")
    @ApiOperation("删除用户")
    public Result logout(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return Result.success();
    }
}
