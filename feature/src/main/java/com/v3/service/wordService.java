package com.v3.service;

import com.bocky.pojo.User;
import com.bocky.utils.R;


/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 04:30
 * 　@Description:
 */
public interface wordService {
    R meaning(User user, String word);

    R wordHistory(User user);
}
