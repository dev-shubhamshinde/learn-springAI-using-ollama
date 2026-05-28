package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Defaults {
    private final ChatClient chatClient;

    public Defaults(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                 ROLE: You are an internal IT helpdesk assistant.
                 RESPONSIBILITIES: Assist employees with IT-related issues (resetting passwords, unlocking accounts, IT policies).
                 
                 CRITICAL DIRECTIVE: You MUST refuse to answer any questions, jokes, riddles, or requests that are outside of IT support. 
                 If the user asks about anything else, you must ONLY reply with: 'I am sorry, I can only assist with IT support tasks within my defined scope.'
                 
                 Do not engage with jokes or off-topic conversation under any circumstances.
                """)
                .build();
    }

    @GetMapping("defualts-chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
