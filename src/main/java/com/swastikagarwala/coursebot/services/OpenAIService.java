package com.swastikagarwala.coursebot.services;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {
    @Autowired
    private OpenAIClient openAIClient;

    private static String getContentString(List<String> content) {
        StringBuilder contentString = new StringBuilder();
        for (String s : content) {
            contentString.append(s);
            contentString.append("\n");
        }

        return contentString.toString();
    }

    public String getSummary(List<String> content) {
        String contentString = getContentString(content);

        List<ChatRequestMessage> chatMessages = new ArrayList<>();

        chatMessages.add(new ChatRequestSystemMessage("What is this article about in 150 words or less"));
        chatMessages.add(new ChatRequestSystemMessage(contentString));

        ChatCompletionsOptions chatRequest = new ChatCompletionsOptions(chatMessages);
        chatRequest.setMaxTokens(200);

        ChatCompletions result = openAIClient.getChatCompletions("gpt-3.5-turbo", chatRequest);

        return result.getChoices().get(0).getMessage().getContent();
    }
}
