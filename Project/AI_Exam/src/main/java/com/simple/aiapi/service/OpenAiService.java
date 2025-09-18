package com.simple.aiapi.service;


import com.simple.aiapi.dto.ChatResponse;
import com.simple.aiapi.dto.QuizPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.aiapi.dto.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizPayload requestJavaBasicsQuiz(int count) {
        String system = "You are a helpful assistant that outputs strictly valid JSON for quizzes.";
        String user = """
            자바 기초 4지선다 문제를 %d개 만들어줘.
            난이도: 초중급 혼합.
            출력은 반드시 JSON(UTF-8)으로만, 아래 스키마를 준수해.
            {
              "questions": [
                {
                  "question": "문항 내용",
                  "options": { "A": "...", "B": "...", "C": "...", "D": "..." },
                  "correct": "A"
                }
              ]
            }
            제약:
            - 각 문항은 보기 텍스트가 중복되지 않게.
            - '정답은 ~' 같은 설명 금지(오직 JSON).
            - 한국어로 작성.
            """.formatted(count);

        ChatRequest req = ChatRequest.builder()
                .model("gpt-4o-mini") // 또는 'gpt-4.1-mini', 필요시 변경
                .messages(List.of(
                        ChatRequest.Message.builder().role("system").content(system).build(),
                        ChatRequest.Message.builder().role("user").content(user).build()
                ))
                .response_format(Map.of("type", "json_object"))
                .build();

        ChatResponse res = openAiWebClient.post()
                .uri("/chat/completions")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        if (res == null || res.getChoices() == null || res.getChoices().isEmpty()) {
            throw new IllegalStateException("OpenAI 응답이 비어 있습니다.");
        }

        String json = res.getChoices().get(0).getMessage().getContent();
        try {
            return objectMapper.readValue(json, QuizPayload.class);
        } catch (Exception e) {
            throw new RuntimeException("OpenAI JSON 파싱 실패: " + e.getMessage() + "\n원본: " + json, e);
        }
    }
}