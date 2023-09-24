package com.v3.controller;

import com.bocky.pojo.User;
import com.bocky.utils.R;
import com.v3.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-22-下午 05:22
 * 　@Description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public R store(@RequestParam("username") String name,
                   @RequestParam("password") String password,
                   @RequestParam("file") MultipartFile file) {
        // 创建一个 User 对象，将数据设置到 User 对象中
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);

        // 调用文件上传服务处理文件
        return fileService.uploadFile(user, file);
    }

    @PostMapping("/uploadcomposition")
    public R storecomposition(@RequestParam("username") String name,
                   @RequestParam("password") String password,
                   @RequestParam("file") MultipartFile file) {
        // 创建一个 User 对象，将数据设置到 User 对象中
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);

        // 调用文件上传服务处理文件
        return fileService.uploadFile(user, file);
    }

    @PostMapping("/fileHistory")
    public R fileHistory(@RequestBody User user) {
        return fileService.fileHistory(user);
    }

}
