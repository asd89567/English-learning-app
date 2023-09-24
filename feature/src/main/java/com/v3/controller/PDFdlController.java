package com.v3.controller;

import com.bocky.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 08:14
 * 　@Description:
 */
@RestController
@RequestMapping("/pdf")
public class PDFController {
    @PostMapping("/point")
    public R point() {
        return null;
    }
}
