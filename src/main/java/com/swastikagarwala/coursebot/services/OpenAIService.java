package com.swastikagarwala.coursebot.services;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import com.azure.json.JsonProviders;
import com.swastikagarwala.coursebot.models.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OpenAIService {
    @Autowired
    private OpenAIClient openAIClient;

    private static String getCombinedString(List<String> strings) {
        StringBuilder combinedString = new StringBuilder();
        for (String s : strings) {
            combinedString.append(s);
            combinedString.append("\n");
        }

        return combinedString.toString();
    }

    public String getSummary(List<String> content) {
        String contentString = getCombinedString(content);

        List<ChatRequestMessage> chatMessages = new ArrayList<>();

        chatMessages.add(new ChatRequestSystemMessage("What is this article about in 150 words or less"));
        chatMessages.add(new ChatRequestSystemMessage(contentString));

        ChatCompletionsOptions chatRequest = new ChatCompletionsOptions(chatMessages);
        chatRequest.setMaxTokens(200);

        ChatCompletions result = openAIClient.getChatCompletions("gpt-3.5-turbo", chatRequest);

        return result.getChoices().get(0).getMessage().getContent();
    }

    public List<Article> createCourse(String prompt, List<Article> articles) {
        List<ChatRequestMessage> chatMessages = new ArrayList<>();

        chatMessages.add(new ChatRequestSystemMessage("Pick articles out of the given ones which would be a good fit for the given prompt. Order them in a way which makes sense. Reply with a list of articleIds in a json format."));
        chatMessages.add(new ChatRequestSystemMessage("Prompt: " + prompt));
        chatMessages.add(new ChatRequestSystemMessage("Articles: " + articles));

        ChatCompletionsOptions chatRequest = new ChatCompletionsOptions(chatMessages);
        chatRequest.setMaxTokens(30);

        try {
            chatRequest.setResponseFormat(ChatCompletionsJsonResponseFormat.fromJson(JsonProviders.createReader(
                    "{\n" +
                            "  \"name\": \"course\",\n" +
                            "  \"description\": \"a course of articles\",\n" +
                            "  \"strict\": true,\n" +
                            "  \"parameters\": {\n" +
                            "    \"type\": \"object\",\n" +
                            "    \"properties\": {\n" +
                            "      \"articleIds\": {\n" +
                            "        \"type\": \"array\",\n" +
                            "        \"description\": \"array of relevant articles based on prompt\",\n" +
                            "        \"items\": {\n" +
                            "          \"type\": \"string\"\n" +
                            "        }\n" +
                            "      }\n" +
                            "    },\n" +
                            "    \"additionalProperties\": false,\n" +
                            "    \"required\": [\"articleIds\"]\n" +
                            "  }\n" +
                            "}"
            )));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ChatCompletions result = openAIClient.getChatCompletions("gpt-4o", chatRequest);
        List<Long> articleIds = getArticleIds(result);

        List<Article> selectedArticles = new ArrayList<>();

        for (Long articleId : articleIds) {
            for (Article article : articles) {
                if (Objects.equals(articleId, article.getArticleId())) {
                    selectedArticles.add(article);
                }
            }
        }

        return selectedArticles;
    }

    private static List<Long> getArticleIds(ChatCompletions result) {
        String resultString = result.getChoices().get(0).getMessage().getContent();

        JSONObject resultObj;
        List<Long> articleIds = new ArrayList<>();

        try {
            resultObj = new JSONObject(resultString);
            JSONArray resultArray = resultObj.getJSONArray("articleIds");

            for (int i = 0; i < resultArray.length(); i++) {
                articleIds.add(resultArray.getLong(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return articleIds;
    }
}
