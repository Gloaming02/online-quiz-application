package com.huz.quizapp.service;

import com.huz.quizapp.dao.QuizDao;
import com.huz.quizapp.dao.QuestionDao;
import com.huz.quizapp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createQuiz(Quiz quiz) {
        return quizDao.createQuiz(quiz);
    }

    public void addQuizQuestion(int quizId, int questionId) {
        quizDao.addQuizQuestion(quizId, questionId);
    }

    public List<QuizQuestion> getQuizQuestions(int quizId) {
        return quizDao.getQuizQuestions(quizId);
    }

    public void updateUserChoice(int qqId, int choiceId) {
        quizDao.updateUserChoice(qqId, choiceId);
    }

    public void finishQuiz(int quizId) {
        quizDao.finishQuiz(quizId);
    }

    public Quiz getQuizById(int quizId) {
        return quizDao.getQuizById(quizId);
    }


    public List<Quiz> getQuizzesByUser(int userId) {
        String sql =
                "SELECT q.quiz_id, q.name, q.time_start, q.time_end, c.name AS category_name " +
                        "FROM Quiz q " +
                        "JOIN Category c ON q.category_id = c.category_id " +
                        "WHERE q.user_id = ? " +
                        "ORDER BY q.time_start DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Quiz quiz = new Quiz();
            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setName(rs.getString("name"));
            quiz.setTimeStart(rs.getTimestamp("time_start"));
            quiz.setTimeEnd(rs.getTimestamp("time_end"));
            quiz.setCategoryName(rs.getString("category_name"));
            return quiz;
        }, userId);
    }


}
