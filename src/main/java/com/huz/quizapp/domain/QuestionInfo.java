package com.huz.quizapp.domain;

import com.huz.quizapp.domain.Choice;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfo {
    private int questionId;
    private String description;
    private boolean isActive;
    private int categoryId;
    private String categoryName;
    private List<Choice> choiceList;
}
