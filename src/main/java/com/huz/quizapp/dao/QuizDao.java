package com.huz.quizapp.dao;

import com.huz.quizapp.domain.Choice;
import com.huz.quizapp.domain.Question;
import com.huz.quizapp.domain.Quiz;
import com.huz.quizapp.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public void addQuizQuestion(int quizId, int questionId) {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, quizId, questionId);
    }

    public List<QuizQuestion> getQuizQuestions(int quizId) {
        String sql = "SELECT * FROM QuizQuestion WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizQuestion.class), quizId);
    }

    public void updateUserChoice(int qqId, int choiceId) {
        String sql = "UPDATE QuizQuestion SET user_choice_id = ? WHERE qq_id = ?";
        jdbcTemplate.update(sql, choiceId, qqId);
    }

    public void finishQuiz(int quizId) {
        String sql = "UPDATE Quiz SET time_end = NOW() WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quizId);
    }

    public Quiz getQuizById(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), quizId);
    }


}
