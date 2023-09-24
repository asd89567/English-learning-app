package com.v3.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bocky.pojo.User;
import com.bocky.utils.R;

import java.io.IOException;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 08:13
 * 　@Description:
 */
public interface PDFdlService {
    /**
     * 處理pdf文檔
     * @return
     */
    public R dlPdf() throws IOException, InterruptedException, Exception;

    public R answersolution() throws IOException, InterruptedException, Exception;

    public R answeremphasis() throws IOException, InterruptedException, Exception;
}
