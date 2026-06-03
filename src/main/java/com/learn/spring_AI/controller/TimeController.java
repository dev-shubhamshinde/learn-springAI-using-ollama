package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
public class TimeController {

    private final ChatClient chatClient;

    public TimeController(@Qualifier("timeChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/local-time")
    public ResponseEntity<String> localTime(@RequestHeader("username") String username,
                                            @RequestParam("message") String message) {
        // Strengthened System Message
        String systemMessage = """
        You are a helpful AI assistant with access to time-related tools.
        IMPORTANT: When the user asks about the current time in any location,
        you MUST call the 'getCurrentTime' tool with the appropriate IANA timezone ID.
        For Pune, India → use timezone: "Asia/Kolkata"
        Do NOT answer from memory. Always use the tool.
        """;

        String answer = chatClient.prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .system(systemMessage)
                .user(message)
                .call().content();
        return ResponseEntity.ok(answer);
    }
}