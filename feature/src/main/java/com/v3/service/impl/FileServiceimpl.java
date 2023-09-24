package com.v3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bocky.pojo.User;
import com.bocky.pojo.file;
import com.bocky.utils.R;
import com.v3.mapper.FileMapper;
import com.v3.mapper.UserMapper;
import com.v3.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-22-下午 05:37
 * 　@Description:
 */
@Service
public class FileServiceimpl implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private UserMapper userMapper;
    String uploadDir = "C://Users//Bocky//Downloads//v3_project//v3_project//feature//src//main//resources//public//file";
    String uploadDirComposition = "C://Users//Bocky//Downloads//v3_project//v3_project//feature//src//main//resources//public//file//composition";
    public R uploadFile(@RequestBody User user, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            // 构建文件保存路径
            String filePath = uploadDir + File.separator + fileName;
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username", user.getUserName());
            User user1 = userMapper.selectOne(userQueryWrapper);
            file file1 = new file(filePath, user1.getUserId());
            int insert = fileMapper.insert(file1);
            if (insert == 0) {
                return R.fail("文件上传失败");
            }else {
            // 将文件保存到指定目录
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
            return R.ok("文件上传成功", fileName);
            }
        } catch (IOException e) {
            return R.fail("文件出現問題");
        }
    }

    public R storecomposition(@RequestBody User user, @RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            // 构建文件保存路径
            String filePath = uploadDirComposition + File.separator + fileName;
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username", user.getUserName());
            User user1 = userMapper.selectOne(userQueryWrapper);
            file file1 = new file(filePath, user1.getUserId());
            int insert = fileMapper.insert(file1);
            if (insert == 0) {
                return R.fail("文件上传失败");
            }else {
                // 将文件保存到指定目录
                byte[] bytes = file.getBytes();
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                return R.ok("文件上传成功", fileName);
            }
        } catch (IOException e) {
            return R.fail("文件出現問題");
        }
    }
    public R fileHistory(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        User user1 = userMapper.selectOne(userQueryWrapper);
        QueryWrapper<file> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("user_id", user1.getUserId());
        List<file> files = fileMapper.selectList(fileQueryWrapper);
        return R.ok("完成", files);
    }
}
