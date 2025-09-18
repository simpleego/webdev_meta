package com.simple.aiapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String text;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("label ASC")
    @Builder.Default
    private List<Choice> choices = new ArrayList<>();

    @Column(length = 1, nullable = false) // 'A'~'D'
    private String correctLabel;

    public void addChoice(Choice c) {
        c.setQuestion(this);
        this.choices.add(c);
    }
}