package com.v3.service;

import com.bocky.pojo.User;
import com.bocky.utils.R;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 05:01
 * 　@Description:
 */
public interface UserService {
   R login(User user);

   R register(User user);
}
