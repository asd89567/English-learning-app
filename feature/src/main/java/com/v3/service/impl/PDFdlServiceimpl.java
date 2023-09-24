package com.v3.service.impl;

import com.bocky.utils.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.v3.mapper.PDFdlMapper;
import com.v3.service.PDFdlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-05-11-下午 08:14
 * 　@Description:
 */
@Service
@Slf4j
public class PDFdlServiceimpl implements PDFdlService{

    @Resource
    private PDFdlMapper pdfdlMapper;

    private static final String URL_GPT_TURBO = "https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "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";

    private static final String REQUEST_BODY = "你現在是一位英文老師，要檢查學生寫的英文作文寫得好不好，我會傳給你一篇英文作文，你要以照以下幾點，用繁體中文回答，回答盡量簡而有力: 可能會出現錯字所以不用提醒(是因為文字辨識錯誤導致的)所以自行修正，注重於文法錯誤需要改進之類的，沒有錯誤的話不用特別寫出來，只需要列出錯誤的部分  以下是我的作文: %s";
    private static final String REQUEST_BODY2 = "你現在是一個英文老師，我會給你英文題目，請給我每題的答案，因為使用ocr辨識文字所以題目單字可能有錯誤，請自行修成合理單字: %s";

    private static final String REQUEST_BODY3 = "你現在是一個英文老師，我會給你英文考試的文章，請以條列的方法將此文章的重點大意、文法、單字還有重點段落等列出來: %s";


    /**
     * 1.讀取pdf
     *
     * @return
     */
//    @Override
//    public R dlPdf() throws IOException {
//        // Load PDF document
//        File file = new File("C:\\Users\\Bocky\\IdeaProjects\\v3_project\\feature\\src\\main\\resources\\public\\imgs\\s1.pdf");
//
//        PDDocument document = PDDocument.load(file);
//        // Create PDFTextStripper object
//        PDFTextStripper pdfStripper = new PDFTextStripper();
//        // Extract text from PDF document
//        String text = pdfStripper.getText(document);
//        // Close the document
//        document.close();
//        log.info(text);
//        return R.ok("完成ss",text);
//    }
//    @Override
//    public R dlPdf() throws Exception {
//            //讀取txt
//            //作文評斷
//            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\composition\\text.txt")));
//            String postBody = REQUEST_BODY.formatted(content);
//            var request = HttpRequest.newBuilder()
//                    .uri(URI.create(URL_GPT_TURBO))
//                    .header("Authorization", "Bearer " + API_KEY)
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
//                    .build();
//            var client = HttpClient.newHttpClient();
//            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response.body());
//            String text= jsonNode.get("choices").get(0).get("message").get("content").toString();
//            log.info(text);
//            return R.ok("完成",text);
//        }

    @Override
    public R dlPdf() throws Exception {
        try {
            // 读取文本文件内容
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\composition\\text.txt")));

            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY.formatted(content));
            messagesArray.add(messageObject);
            requestJson.set("messages", messagesArray);
            requestJson.put("temperature", 0);

            // 将请求体转换为字符串
            String postBody = objectMapper.writeValueAsString(requestJson);

            // 发送请求到 OpenAI API
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GPT_TURBO))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
                    .build();
            var client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 解析API响应
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String text = jsonResponse.get("choices").get(0).get("message").get("content").asText();
            log.info(text);

            return R.ok("完成", text);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("发生IO异常");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("发生异常");
        }
    }

    @Override
    public R answersolution() throws Exception {
        try {
            // 读取文本文件内容
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\solution\\text.txt")));

            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY2.formatted(content));
            messagesArray.add(messageObject);
            requestJson.set("messages", messagesArray);
            requestJson.put("temperature", 0);

            // 将请求体转换为字符串
            String postBody = objectMapper.writeValueAsString(requestJson);

            // 发送请求到 OpenAI API
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GPT_TURBO))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
                    .build();
            var client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 解析API响应
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String text = jsonResponse.get("choices").get(0).get("message").get("content").asText();
            log.info(text);

            return R.ok("完成", text);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("发生IO异常");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("发生异常");
        }
    }
    @Override
    public R answeremphasis() throws Exception {
        try {
            // 读取文本文件内容
            String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Bocky\\Downloads\\v3_project\\v3_project\\feature\\src\\main\\resources\\public\\file\\emphasis\\text.txt")));

            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY3.formatted(content));
            messagesArray.add(messageObject);
            requestJson.set("messages", messagesArray);
            requestJson.put("temperature", 0);

            // 将请求体转换为字符串
            String postBody = objectMapper.writeValueAsString(requestJson);

            // 发送请求到 OpenAI API
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_GPT_TURBO))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
                    .build();
            var client = HttpClient.newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 解析API响应
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String text = jsonResponse.get("choices").get(0).get("message").get("content").asText();
            log.info(text);

            return R.ok("完成", text);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("发生IO异常");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("发生异常");
        }
    }

    public R filehistory() throws Exception {
        return R.ok("完成");
    }

}
