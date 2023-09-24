package com.v3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bocky.constant.UserConstant;
import com.bocky.pojo.User;
import com.bocky.utils.MD5Util;
import com.bocky.utils.R;
import com.v3.mapper.UserMapper;
import com.v3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 05:07
 * 　@Description:
 */
@Service
public class UserServiceimpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 1.密碼加密
     * 2.帳號密碼資料庫查詢,返回一個user對象
     * 3.判斷結果
     * @param
     * @return
     */
    @Override
    public R login(User user) {
        String pwd = MD5Util.encode(user.getPassword() + UserConstant.User_SLAT);


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUserName()).eq("password",pwd);
        User loginUser = userMapper.selectOne(queryWrapper);

        if (loginUser==null){
            return R.fail("登陸失敗");
        }
        loginUser.setPassword(null);

        return R.ok("登陸成功",loginUser.getUserName(),null);
    }

    /**
     * 註冊業務
     *  1.檢查帳號是否存在
     *  2.密碼加密
     *  3.擦入資料庫
     *  4.返回解果封裝
     * @param user 參數以轎驗，密碼還為明文
     * @return
     */
    @Override
    public R register(User user) {

        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username",user.getUserName());
        Long total = userMapper.selectCount(userWrapper);
        if (total>0){
            return R.fail("帳號已存在");
        }
        //2 MD5
        String encode = MD5Util.encode(user.getPassword() + UserConstant.User_SLAT);
        user.setPassword(encode);

        int insert = userMapper.insert(user);

        if (insert==1){
            return R.ok("註冊成功");
        }
        return R.fail("註冊失敗");
    }
}
