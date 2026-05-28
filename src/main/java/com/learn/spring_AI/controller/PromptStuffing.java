package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffing {
    private final ChatClient chatClient;

    public PromptStuffing(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    
    String promptStuffing = """
            You are an internal HR assistant. You assist employees with queries related to HR policies
            only — such as leave entitlements, working hours, benefits, and code of conduct.
            HR Policy Summary:
            • 18 days of paid leave annually
            • Up to 8 unused leave days can be carried over to the next year
            • Standard working hours: 9 AM to 6 PM, Monday to Friday
            • Notice period - 30 days
            • Maternity leaves - 6 months
            • Paternity leaves - 2 weeks
            • National holidays are company-wide off days
            • Benefits include health insurance, provident fund, and annual health checkup
            • Employees must adhere to professional behavior, punctuality, and data confidentiality
            
            Politely inform users that you can only help with HR policy-related topics if they ask
            something outside your scope.
    """;

   

    @GetMapping("prompt-stuffing")
    public String promptStuffingMeth(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .system(promptStuffing)
                .user(message)
                .call()
                .content();
    }
}
