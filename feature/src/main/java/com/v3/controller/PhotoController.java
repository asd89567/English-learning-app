package com.v3.controller;

import com.bocky.pojo.photo;
import com.bocky.utils.R;
import com.v3.service.PhotoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-18-下午 06:10
 * 　@Description:
 */

@RequestMapping("/photo")
@RestController
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @PostMapping("/answer")
    public R answer(@RequestBody photo pt) {
       return photoService.answer(pt.getUsername(), pt.getPhoto());
    }

}
