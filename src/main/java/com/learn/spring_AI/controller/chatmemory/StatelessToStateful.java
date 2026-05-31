package com.learn.spring_AI.controller.chatmemory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatelessToStateful {
    private final ChatClient chatClient;

    public StatelessToStateful(ChatClient.Builder builder, ChatMemory chatMemory) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = builder
                .defaultAdvisors(memoryAdvisor)
                .build();
    }

    @GetMapping("chat-memory")
    public ResponseEntity<String> chatmemory(
            @RequestParam("message") String message) {
        return ResponseEntity.ok(chatClient.prompt()
                .user(message)
                .call()
                .content());
    }
}
