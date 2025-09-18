package com.simple.aiapi.controller;

import com.simple.aiapi.domain.Question;
import com.simple.aiapi.service.QuizService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Validated
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        List<Question> questions = quizService.findAll();
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam(defaultValue = "5") @Min(1) @Max(20) int count) {
        quizService.generateAndSave(count);
        return "redirect:/";
    }

    @PostMapping("/submit")
    public String submit(@RequestParam Map<String, String> params, Model model) {
        // params key 형식: answer_{questionId} = "A"~"D"
        Map<Long, String> answers = new HashMap<>();
        params.forEach((k, v) -> {
            if (k.startsWith("answer_")) {
                try {
                    Long qid = Long.valueOf(k.substring("answer_".length()));
                    answers.put(qid, v);
                } catch (NumberFormatException ignored) {}
            }
        });
        int total = quizService.findAll().size();
        int correct = quizService.grade(answers);
        model.addAttribute("score", correct);
        model.addAttribute("total", total);
        model.addAttribute("questions", quizService.findAll());
        model.addAttribute("answers", answers);
        return "result";
    }
}