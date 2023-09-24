package com.v3.service;

import com.bocky.pojo.User;
import com.bocky.utils.R;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 03:19
 * 　@Description:
 */
public interface ProblemService {
    R word_questions(User user);

    R grammar(User user);

    R problemHistory(User user, String problemType);
}
