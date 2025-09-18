package com.simple.aiapi.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private Map<String, Object> response_format; // {"type": "json_object"}

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Message {
        private String role;    // "system", "user"
        private String content;
    }
}
