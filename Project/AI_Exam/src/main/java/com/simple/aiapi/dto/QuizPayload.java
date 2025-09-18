package com.simple.aiapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuizPayload {
    private List<Item> questions;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Item {
        private String question;
        private Options options;
        private String correct; // "A" | "B" | "C" | "D"
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Options {
        @JsonProperty("A")
        private String A;

        @JsonProperty("B")
        private String B;

        @JsonProperty("C")
        private String C;

        @JsonProperty("D")
        private String D;
    }
}