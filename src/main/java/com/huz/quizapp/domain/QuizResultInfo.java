package com.huz.quizapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class QuizResultInfo {
    private String quizName;
    private String fullName;
    private Timestamp startTime;
    private Timestamp endTime;
}
