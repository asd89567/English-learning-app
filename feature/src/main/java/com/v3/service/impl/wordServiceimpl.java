package com.v3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bocky.pojo.User;
import com.bocky.pojo.Word;
import com.bocky.utils.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.v3.mapper.UserMapper;
import com.v3.mapper.WordMapper;
import com.v3.service.wordService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 04:30
 * 　@Description:
 */
@Service
public class wordServiceimpl implements wordService {
    @Resource
    private UserMapper userMapper;
    @Resource
    public WordMapper wordMapper;

    private static final String URL_GPT_TURBO = "https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "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";

    private static final String REQUEST_BODY = "你現在是一本英文字典，我會給你一個英文單字，你幫我翻譯，把它的字根字首拆開來解釋，並幫我跟該單字做聯想，嚴格按照下面範例的格式，( )內的字是註解，千萬不要跟著打出來，回答內容需包含記憶方法和同字根單字，回答只需按照範例格式輸出，不用跟我聊天 : \n" +
            "\n" +
            "word:\n" +
            "XXXX(要問的單字) - XXXX(該單字的中文翻譯)\n" +
            " * 字首: XXXX (該單字的字首，這部分用中文回答)\n" +
            "\n" +
            "* 字根: XXXX( 該單字的字根，這部分用中文回答)\n" +
            "\n" +
            "* 字尾: XXXX(該單字的字尾，這部分用中文回答)\n" +
            "\n" +
            "help:\n" +
            "XXXX(該單字如何用字根字首做聯想，這部分用中文回答)\n" +
            "\n" +
            "similar:\n" +
            "1. XXXX(相同字根的單字)-XXXX(該單字的中文翻譯，不用解釋字根字首)\n" +
            "2. XXXX(相同字根的單字)-XXXX(該單字的中文翻譯，不用解釋字根字首)\n" +
            "3. XXXX(相同字根的單字)-XXXX(該單字的中文翻譯，不用解釋字根字首)\n" +
            "\n" +
            "以下是我要問的單字:\n" +
            "%s";

    @Override
    public R meaning(User user, String word) {
        try {
            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY.formatted(word));
            messagesArray.add(messageObject);
            requestJson.set("messages", messagesArray);
            requestJson.put("temperature", 0.6);

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
            String userName = user.getUserName();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            User user1 = userMapper.selectOne(queryWrapper.eq("username", userName));
            System.out.println(text);
            int grammar = wordMapper.insert(new Word(0,word,text,user1.getUserId()));
            if (grammar == 0) {
                return R.fail("插入失败");
            }

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
    public R wordHistory(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        User user1 = userMapper.selectOne(userQueryWrapper);
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.eq("user_id", user1.getUserId());
        return R.ok(wordMapper.selectList(wordQueryWrapper));
    }

}
