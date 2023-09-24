package com.v3.controller;

import com.bocky.DTO.UserAndProblemType;
import com.bocky.DTO.UserAndwordNameRequest;
import com.bocky.pojo.User;
import com.bocky.pojo.Word;
import com.bocky.utils.R;
import com.v3.service.wordService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 04:26
 * 　@Description:
 */
@RestController
@RequestMapping("/word")
public class wordController {
    @Resource
    public wordService wordService;

    @PostMapping("/meaning")
        public R word(@RequestBody @Validated UserAndwordNameRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return R.fail(bindingResult.getFieldError().getDefaultMessage());
        }
      return wordService.meaning(request.user,request.wordName);
    }

    @PostMapping("/wordHistory")
    public R wordHistory(@RequestBody User user) {
        return wordService.wordHistory(user);
    }
}
