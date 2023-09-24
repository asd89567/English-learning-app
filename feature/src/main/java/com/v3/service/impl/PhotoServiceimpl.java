package com.v3.service.impl;

import com.bocky.utils.R;
import com.v3.service.PhotoService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-18-下午 06:14
 * 　@Description:
 */
@Service
public class PhotoServiceimpl implements PhotoService {
    @Override
    public R answer(String username, String photoName ) {
        try {
            // 讀取照片轉文字
            String fileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\composition\\" + photoName;
            File file = new File(fileName);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
            tesseract.setLanguage("eng");
            String result = tesseract.doOCR(file);
            writeObj(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("msg", "完成", null);
    }
    @Override
    public R changtext(String username, String photoName ) {
        try {
            // 讀取照片轉文字
            String fileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\solution\\" + photoName;
            File file = new File(fileName);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
            tesseract.setLanguage("eng");
            String result = tesseract.doOCR(file);
            writesolution(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("msg", "完成", null);
    }

    @Override
    public R emphasischangtext(String username, String photoName) {
        try {
            // 讀取照片轉文字
            String fileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\emphasis\\" + photoName;
            File file = new File(fileName);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
            tesseract.setLanguage("eng");
            String result = tesseract.doOCR(file);
            writeemphasis(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("msg", "完成", null);
    }


    public void writeObj(String result) throws IOException {
        String newFileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\composition\\text.txt";
        try {
            // 防止文件建立或讀取失敗，用catch捕捉錯誤並打印，也可以throw
            /* 寫入Txt文件 */
            File writename = new File(newFileName);// 相對路徑，如果沒有則要建立一個新的output。txt文件
            writename.createNewFile(); // 創建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
            out.write(result);

            System.out.println("寫入成功！");
            out.flush(); // 把緩存區內容壓入文件
            out.close(); // 最後記得關閉文件

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void writesolution(String result) throws IOException {
        String newFileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\solution\\text.txt";
        try {
            // 防止文件建立或讀取失敗，用catch捕捉錯誤並打印，也可以throw
            /* 寫入Txt文件 */
            File writename = new File(newFileName);// 相對路徑，如果沒有則要建立一個新的output。txt文件
            writename.createNewFile(); // 創建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
            out.write(result);

            System.out.println("寫入成功！");
            out.flush(); // 把緩存區內容壓入文件
            out.close(); // 最後記得關閉文件

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void writeemphasis(String result) throws IOException {
            String newFileName = "C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\emphasis\\text.txt";
            try {
            // 防止文件建立或讀取失敗，用catch捕捉錯誤並打印，也可以throw
            /* 寫入Txt文件 */
            File writename = new File(newFileName);// 相對路徑，如果沒有則要建立一個新的output。txt文件
            writename.createNewFile(); // 創建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, false));
            out.write(result);

            System.out.println("寫入成功！");
            out.flush(); // 把緩存區內容壓入文件
            out.close(); // 最後記得關閉文件

            } catch (Exception e) {
            e.printStackTrace();
            }

            }

}
