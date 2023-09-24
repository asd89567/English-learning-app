package com.v3.controller;

import com.bocky.DTO.UserAndFileNameRequest;
import com.bocky.pojo.User;
import com.bocky.pojo.file;
import com.bocky.utils.R;
import com.v3.service.PhotoService;
import com.v3.service.impl.PDFdlServiceimpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 08:14
 * 　@Description:
 */
@RestController
@RequestMapping("/pdf")
public class PDFdlController {

    @Resource
    private PDFdlServiceimpl pdfdlService;

    @Resource
    private PhotoService photoService;
    @PostMapping("/point")//作文評斷(完成
    public R point(@RequestBody @Validated UserAndFileNameRequest requests, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return R.fail("操作失敗");
        }
        R answer = photoService.answer(requests.user.getUserName(), requests.fileName);
        if (answer.getCode() == "001") {
            R r = pdfdlService.dlPdf();
            return r;
        }
        return R.fail("操作失敗");
    }

    @PostMapping("/solution")//題目獲取詳解(完成
    public R solution(@RequestBody @Validated UserAndFileNameRequest requests, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return R.fail("操作失敗");
        }
        R answer = photoService.changtext(requests.user.getUserName(), requests.fileName);//
        if (answer.getCode() == "001") {
            R r = pdfdlService.answersolution();
            return r;
        }
        return R.fail("操作失敗");
    }
    @PostMapping("/emphasis")//文章重點整理
    public R emphasis(@RequestBody @Validated UserAndFileNameRequest requests, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return R.fail("操作失敗");
        }
        R answer = photoService.emphasischangtext(requests.user.getUserName(), requests.fileName);
        if (answer.getCode() == "001") {
            R r = pdfdlService.answeremphasis();
            return r;
        }
        return R.fail("操作失敗");
    }
}
