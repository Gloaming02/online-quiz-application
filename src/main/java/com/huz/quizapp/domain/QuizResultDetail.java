package com.huz.quizapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDetail {
    private int qqId;
    private String question;
    private String userChoice;
    private boolean isCorrect;
    private String correctAnswer;

    private int questionId;
    private int selectedChoiceId;

    private List<Choice> choices;
}
