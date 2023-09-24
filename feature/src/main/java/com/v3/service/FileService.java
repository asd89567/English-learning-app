package com.v3.service;

import com.bocky.pojo.User;
import com.bocky.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-22-下午 05:38
 * 　@Description:
 */
public interface FileService {
    R uploadFile(User user , MultipartFile file);

    R storecomposition(User user , MultipartFile file);

    R fileHistory(User user);
}
