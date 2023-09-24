package com.v3.service;

import com.bocky.utils.R;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-18-下午 06:11
 * 　@Description:
 */
public interface PhotoService {

    R answer(String username, String photo);
    R changtext(String username, String photo);

    R emphasischangtext(String username, String photo);
}
