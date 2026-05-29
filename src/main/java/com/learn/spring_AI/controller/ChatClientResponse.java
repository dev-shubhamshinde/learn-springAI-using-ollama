package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatClientResponse {

    private final ChatClient chatClient;

    public ChatClientResponse(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("chat-response")
    public ChatResponse chat(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .call()
                .chatResponse();
    }
}
