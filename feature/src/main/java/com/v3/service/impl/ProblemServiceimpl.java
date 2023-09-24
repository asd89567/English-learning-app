package com.v3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bocky.pojo.Problem;
import com.bocky.pojo.User;
import com.bocky.utils.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.v3.mapper.ProblemMapper;
import com.v3.mapper.UserMapper;
import com.v3.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 　@Author: Bocky
 * 　@Date: 2023-09-23-上午 03:19
 * 　@Description:
 */
@Service
public class ProblemServiceimpl implements ProblemService {

    @Resource
    private ProblemMapper  problemMapper;

    @Resource
    private UserMapper userMapper;

    private static final String URL_GPT_TURBO = "https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "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";

    private static final String REQUEST_BODY = "你現在是一個英文老師，請出4道選擇題給我，關於英文文法的題目(程度設在多益toeic 700分左右)，並要附上答案與詳解，每題最後用 hihi 做結尾，出題格式如下所式(題目不一定要跟範例一樣為選擇正確的句子，可以穿插填空之類的題型):\n" +
            "\n" +
            "題目0：Which sentence is grammatically correct?\n" +
            "\n" +
            "a) He don't like coffee.\n" +
            "b) He doesn't likes coffee.\n" +
            "c) He doesn't like coffee.\n" +
            "d) He don't likes coffee.\n" +
            "\n" +
            "答案：c) He doesn't like coffee.\n" +
            "\n" +
            "詳解：在英文中，第三人稱單數主詞（he、she、it）搭配動詞時，動詞需加上 \"-s\" 或 \"-es\"。因此，正確的句子是 \"He doesn't like coffee.\" hihi";

    private static final String REQUEST_BODY1 = "你現在是一個英文老師，請出3道選擇題或者句子填空題是關於英文'單字'的題目(程度設在多益toeic 700分左右)，並要附上答案與詳解，每題最後用 hihi 做結尾，出題格式如下所式(題型不一定要跟範例一樣):\n" +
            "\n" +
            "題目1：在下列選項中，選出與「diligent」意思最接近的單字。\n" +
            "\n" +
            "a) lazy\n" +
            "b) hardworking\n" +
            "c) careless\n" +
            "d) inactive\n" +
            "\n" +
            "答案：b) hardworking\n" +
            "\n" +
            "詳解：「diligent」的意思是「勤奮的」或「刻苦的」，與「hardworking」意思相近，表示對工作或學習認真努力。其他選項的意思與「diligent」相反或不相符，因此選擇b) hardworking為正確答案。 hihi";

    @Override
    public R word_questions(User user) {
        try {
            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY1);
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
            int grammar = problemMapper.insert(new Problem(REQUEST_BODY1, text, "word", user1.getUserId()));
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
    public R grammar(User user) {
        try {
            // 构建请求体的JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestJson = objectMapper.createObjectNode();
            requestJson.put("model", "gpt-3.5-turbo");
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode();
            messageObject.put("role", "user");
            messageObject.put("content", REQUEST_BODY);
            messagesArray.add(messageObject);
            requestJson.set("messages", messagesArray);
            requestJson.put("temperature", 0.7);

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
            int grammar = problemMapper.insert(new Problem(REQUEST_BODY, text, "grammar", user1.getUserId()));
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
    public R problemHistory(User user, String problemType) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        User user1 = userMapper.selectOne(userQueryWrapper);
        List<Problem> problems = problemMapper.selectList(new QueryWrapper<Problem>().eq("user_id", user1.getUserId()).eq("problem_type", problemType));

        return R.ok("完成", problems);   }
}
