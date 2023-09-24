package com.v3.controller;

import com.bocky.DTO.UserAndProblemType;
import com.bocky.pojo.User;
import com.bocky.utils.R;
import com.v3.service.ProblemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 03:13
 * 　@Description:
 */
@RequestMapping("/problem")
@RestController
public class ProblemController {
    @Resource
    public ProblemService problemService;

    @PostMapping("/word_questions")
    public R word_questions(@RequestBody User user){
        return problemService.word_questions(user);
    }
    @PostMapping("/grammer")
    public R grammar(@RequestBody User user){
        return problemService.grammar(user);
    }


    @PostMapping("/problemHistory")
    public R problemHistory(@RequestBody UserAndProblemType user) {
        return problemService.problemHistory(user.user, user.problemType);
    }
}
