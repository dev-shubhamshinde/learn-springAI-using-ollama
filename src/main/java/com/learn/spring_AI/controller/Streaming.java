package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class Streaming {
    private final ChatClient chatClient;

    public Streaming(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/streaming")
    public Flux<String> Stream(@RequestParam("message") String message) {
        return chatClient.prompt().user(message).stream().content();
    }
}
