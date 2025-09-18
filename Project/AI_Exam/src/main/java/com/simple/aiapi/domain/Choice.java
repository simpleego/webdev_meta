package com.simple.aiapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Choice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "question_id")
    private Question question;

    @Column(length = 1, nullable = false) // 'A','B','C','D'
    private String label;

    @Column(length = 1000, nullable = false)
    private String text;
}