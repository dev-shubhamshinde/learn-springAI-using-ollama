package com.learn.spring_AI.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StructuredOutput {

    private final ChatClient chatClient;

    public StructuredOutput(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public record CountryCities(
            @JsonProperty("country") String country,
            @JsonProperty("cities") List<String> cities
    ) {
    }

    @GetMapping("structured-output")
    public ResponseEntity<CountryCities> getCountryCities(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient
                .prompt()
                .user(message)
                .call().entity(CountryCities.class);

        return ResponseEntity.ok(countryCities);
    }
}