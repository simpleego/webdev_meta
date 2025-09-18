package com.simple.aiapi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private List<Choice> choices;

    @Getter @Setter
    public static class Choice {
        private Message message;
    }

    @Getter @Setter
    public static class Message {
        private String role;
        private String content; // JSON string (we asked for json_object)
    }
}