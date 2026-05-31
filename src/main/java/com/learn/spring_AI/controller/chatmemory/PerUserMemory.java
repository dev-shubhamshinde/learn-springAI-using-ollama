package com.learn.spring_AI.controller.chatmemory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PerUserMemory {
    private final ChatClient chatClient;

    public PerUserMemory(ChatClient.Builder builder, ChatMemory chatMemory) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = builder
                .defaultAdvisors(memoryAdvisor)
                .build();
    }

    @GetMapping("per-memory-user")
    public ResponseEntity<String> chatmemory(
            @RequestParam("message") String message,
            @RequestHeader("username") String username) {

        return ResponseEntity.ok(chatClient.prompt()
                .user(message)
                // Updated to the new constant location
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .call()
                .content());
    }
}