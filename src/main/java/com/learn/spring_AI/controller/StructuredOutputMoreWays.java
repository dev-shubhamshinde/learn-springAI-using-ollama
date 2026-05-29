package com.learn.spring_AI.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputMoreWays {

    private final ChatClient chatClient;

    public StructuredOutputMoreWays(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public record CountryCities(
            @JsonProperty("country") String country,
            @JsonProperty("cities") List<String> cities
    ) {
    }

    @GetMapping("structured-output-list")
    public ResponseEntity<List<String>> getCountryCitiesByList(@RequestParam("message") String message) {
        // Appending a strict instruction to prevent markdown wrapping
        String strictPrompt = message + "\nIMPORTANT: Return ONLY valid, raw JSON. Do NOT wrap the response in markdown formatting or backticks (```).";

        List<String> countryCities = chatClient
                .prompt()
                .user(strictPrompt)
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {});

        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("structured-output-map")
    public ResponseEntity<Map<String, Object>> getCountryCitiesByMap(@RequestParam("message") String message) {
        // Appending a strict instruction to prevent markdown wrapping
        String strictPrompt = message + "\nIMPORTANT: Return ONLY valid, raw JSON. Do NOT wrap the response in markdown formatting or backticks (```).";

        Map<String, Object> countryCities = chatClient
                .prompt()
                .user(strictPrompt)
                .call()
                .entity(new ParameterizedTypeReference<Map<String, Object>>() {});

        return ResponseEntity.ok(countryCities);
    }
//
//    // BONUS: Using the record you already declared (Highly Recommended)
//    @GetMapping("structured-output-record")
//    public ResponseEntity<CountryCities> getCountryCitiesByRecord(@RequestParam("message") String message) {
//        CountryCities countryCities = chatClient
//                .prompt()
//                .user(message)
//                .call()
//                .entity(CountryCities.class); // Spring AI handles custom records perfectly
//
//        return ResponseEntity.ok(countryCities);
//    }
}