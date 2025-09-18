package com.simple.aiapi.repo;

import com.simple.aiapi.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}