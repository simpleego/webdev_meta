package com.simple.aiapi.service;

import com.simple.aiapi.domain.Choice;
import com.simple.aiapi.domain.Question;
import com.simple.aiapi.dto.QuizPayload;
import com.simple.aiapi.repo.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final OpenAiService openAiService;

    @Transactional
    public List<Question> generateAndSave(int count) {
        QuizPayload payload = openAiService.requestJavaBasicsQuiz(count);

        List<Question> toSave = new ArrayList<>();
        for (QuizPayload.Item item : payload.getQuestions()) {
            Question q = Question.builder()
                    .text(item.getQuestion())
                    .correctLabel(item.getCorrect())
                    .createdAt(LocalDateTime.now())
                    .build();

            Map<String, String> opt = Map.of(
                    "A", item.getOptions().getA(),
                    "B", item.getOptions().getB(),
                    "C", item.getOptions().getC(),
                    "D", item.getOptions().getD()
            );
            opt.forEach((label, text) -> q.addChoice(
                    Choice.builder().label(label).text(text).build()
            ));
            toSave.add(q);
        }
        return questionRepository.saveAll(toSave);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public int grade(Map<Long, String> userAnswers) {
        List<Question> all = questionRepository.findAll();
        int score = 0;
        for (Question q : all) {
            String user = userAnswers.get(q.getId());
            if (user != null && user.equalsIgnoreCase(q.getCorrectLabel())) {
                score++;
            }
        }
        return score;
    }
}