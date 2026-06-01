package com.learn.spring_AI.RAG.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;
@RestController
@RequestMapping("/rag/api")
public class RAGController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    // ✅ Inject the pre-configured ChatClient bean, not the builder
    public RAGController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(
            @RequestHeader("username") String username,
            @RequestParam("message") String message) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.5)
                .build();

        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);

//         ✅ Guard: if nothing found, return default message immediately
        if (similarDocs == null || similarDocs.isEmpty()) {
            return ResponseEntity.ok("I don't know");
        }

        String similarContext = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient.prompt()
                .system(spec -> spec
                        .text(promptTemplate)
                        .param("documents", similarContext))
                .advisors(a -> a.param(CONVERSATION_ID, username)) // ✅ now works, advisor is registered
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}