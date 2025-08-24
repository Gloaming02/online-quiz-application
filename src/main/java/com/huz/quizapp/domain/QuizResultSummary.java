package com.huz.quizapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class QuizResultSummary {
    private int quizResultId;
    private Timestamp takenTime;
    private String categoryName;
    private String fullName;
    private int numQuestions;
    private int score;
}
