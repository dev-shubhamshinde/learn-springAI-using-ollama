package com.learn.spring_AI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatOption {
    private final ChatClient chatClient;

    public ChatOption(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * ChatOptions with a limit on the number of tokens (words) in the response.
     * This forces the AI to provide a concise answer.
     */
    private final ChatOptions chatOptionsWithMaxTokens = ChatOptionsBuilder.builder()
            .withMaxTokens(10)
            .build();

    /**
     * ChatOptions with a higher temperature to make the response more creative and less deterministic.
     * A higher temperature (e.g., 0.9) results in more diverse responses, while a lower value makes it more focused.
     */
    private final ChatOptions chatOptionsWithTemperature = ChatOptionsBuilder.builder()
            .withTemperature(0.9)
            .build();

    /**
     * ChatOptions with Top-K sampling. The model will only consider the top K most likely tokens at each step.
     * A Top-K of 1 means the model will always choose the most likely next token (greedy decoding).
     */
    private final ChatOptions chatOptionsWithTopK = ChatOptionsBuilder.builder()
            .withTopK(1)
            .build();

    /**
     * ChatOptions with a frequency penalty. This discourages the model from repeating the same token.
     * A higher value increases the penalty.
     */
    private final ChatOptions chatOptionsWithFrequencyPenalty = ChatOptionsBuilder.builder()
            .withFrequencyPenalty(0.5)
            .build();

    /**
     * ChatOptions with a presence penalty. This discourages the model from introducing new topics.
     * A higher value increases the penalty.
     */
    private final ChatOptions chatOptionsWithPresencePenalty = ChatOptionsBuilder.builder()
            .withPresencePenalty(0.5)
            .build();

    /**
     * ChatOptions with Top-P (nucleus) sampling. The model considers tokens from a cumulative probability mass.
     * A Top-P of 0.9 means the model will only consider the tokens that make up the top 90% of the probability mass.
     */
    private final ChatOptions chatOptionsWithTopP = ChatOptionsBuilder.builder()
            .withTopP(0.9)
            .build();

    @GetMapping("chat-options-maxtoken")
    public String chatOptions(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithMaxTokens)
                .call()
                .content();
    }

    @GetMapping("chat-options-temperature")
    public String chatOptionsWithTemperature(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithTemperature)
                .call()
                .content();
    }

    @GetMapping("chat-options-topk")
    public String chatOptionsWithTopK(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithTopK)
                .call()
                .content();
    }

    @GetMapping("chat-options-frequencypenalty")
    public String chatOptionsWithFrequencyPenalty(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithFrequencyPenalty)
                .call()
                .content();
    }

    @GetMapping("chat-options-presencepenalty")
    public String chatOptionsWithPresencePenalty(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithPresencePenalty)
                .call()
                .content();
    }

    @GetMapping("chat-options-topp")
    public String chatOptionsWithTopP(@RequestParam("message") String message) {
        return chatClient.prompt(message)
                .options(chatOptionsWithTopP)
                .call()
                .content();
    }
}