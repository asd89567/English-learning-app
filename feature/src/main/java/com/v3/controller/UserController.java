package com.v3.controller;

import com.bocky.pojo.User;
import com.bocky.utils.R;
import com.v3.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 05:01
 * 　@Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody @Validated User user, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.fail("登入失敗，沒有此帳戶");
        }
        return userService.login(user);
    }
    @PostMapping("/register")
    public R register(@RequestBody @Validated User user, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.fail("登入失敗，沒有此帳戶");
        }
        return userService.register(user);
    }
}
