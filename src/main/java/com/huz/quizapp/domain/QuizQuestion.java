package com.huz.quizapp.domain;

public class QuizQuestion {
    private int qqId;
    private int quizId;
    private int questionId;
    private Integer userChoiceId; // 允许 null

    public int getQqId() {
        return qqId;
    }

    public void setQqId(int qqId) {
        this.qqId = qqId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Integer getUserChoiceId() {
        return userChoiceId;
    }

    public void setUserChoiceId(Integer userChoiceId) {
        this.userChoiceId = userChoiceId;
    }
}
