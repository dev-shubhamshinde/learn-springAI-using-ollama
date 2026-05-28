package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.core.Ordered; // <-- Import this
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Advisor {
    private final ChatClient chatClient;

    public Advisor(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/safeguard")
    public String checkSafeGuard(@RequestParam("message") String message) {
        List<String> prohibitedWords = List.of("weapons", "violence", "illegal", "stocks", "investment");

        return chatClient
                .prompt()
                .user(message)
                // Added the 3rd argument (execution order)
                .advisors(new SafeGuardAdvisor(
                        prohibitedWords,
                        "I cannot assist with this request.",
                        Ordered.LOWEST_PRECEDENCE))
                .call()
                .content();
    }
}