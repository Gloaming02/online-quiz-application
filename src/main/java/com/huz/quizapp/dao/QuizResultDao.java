package com.huz.quizapp.dao;

import com.huz.quizapp.domain.QuizResultDetail;
import com.huz.quizapp.domain.QuizResultInfo;
import com.huz.quizapp.domain.QuizResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionDao questionDao;

    public List<QuizResultDetail> getQuizResultDetails(int quizId) {
        String sql =
                "SELECT qq.qq_id, qq.question_id, qq.user_choice_id, " +
                        "       q.description AS question, " +
                        "       uc.description AS user_choice, " +
                        "       uc.is_correct, " +
                        "       c.description AS correct_answer " +
                        "FROM QuizQuestion qq " +
                        "JOIN Question q ON qq.question_id = q.question_id " +
                        "LEFT JOIN Choice uc ON qq.user_choice_id = uc.choice_id " +
                        "JOIN Choice c ON c.question_id = q.question_id AND c.is_correct = TRUE " +
                        "WHERE qq.quiz_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            QuizResultDetail detail = new QuizResultDetail();
            detail.setQqId(rs.getInt("qq_id"));
            detail.setQuestionId(rs.getInt("question_id"));
            detail.setQuestion(rs.getString("question"));
            detail.setUserChoice(rs.getString("user_choice"));
            detail.setSelectedChoiceId(rs.getInt("user_choice_id"));
            detail.setCorrect(rs.getBoolean("is_correct"));
            detail.setCorrectAnswer(rs.getString("correct_answer"));

            detail.setChoices(questionDao.findChoicesByQuestionId(detail.getQuestionId()));

            return detail;
        }, quizId);
    }

    public QuizResultInfo getQuizInfo(int quizId) {
        String sql =
                "SELECT q.name AS quiz_name, " +
                        "       u.firstname, " +
                        "       u.lastname, " +
                        "       q.time_start, " +
                        "       q.time_end " +
                        "FROM Quiz q " +
                        "JOIN User u ON q.user_id = u.user_id " +
                        "WHERE q.quiz_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new QuizResultInfo(
                rs.getString("quiz_name"),
                rs.getString("firstname") + " " + rs.getString("lastname"),
                rs.getTimestamp("time_start"),
                rs.getTimestamp("time_end")
        ), quizId);
    }

    public List<QuizResultSummary> findAllSummaries() {
        String sql =
        "SELECT q.quiz_id AS quiz_result_id, " +
                "       q.time_end AS taken_time, " +
                "       c.name AS category_name, " +
                "       CONCAT(u.firstname, ' ', u.lastname) AS full_name, " +
                "       COUNT(DISTINCT qq.question_id) AS num_questions, " +
                "       SUM(CASE WHEN ch.is_correct THEN 1 ELSE 0 END) AS score " +
                "FROM Quiz q " +
                "JOIN Category c ON q.category_id = c.category_id " +
                "JOIN User u ON q.user_id = u.user_id " +
                "LEFT JOIN QuizQuestion qq ON q.quiz_id = qq.quiz_id " +
                "LEFT JOIN Choice ch ON qq.user_choice_id = ch.choice_id " +
                "GROUP BY q.quiz_id, q.time_end, c.name, u.firstname, u.lastname " +
                "ORDER BY q.time_end DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new QuizResultSummary(
                rs.getInt("quiz_result_id"),
                rs.getTimestamp("taken_time"),
                rs.getString("category_name"),
                rs.getString("full_name"),
                rs.getInt("num_questions"),
                rs.getInt("score")
        ));
    }

    public List<QuizResultSummary> findAllSummaries(Integer categoryId, Integer userId) {
        StringBuilder sql = new StringBuilder(
                "SELECT q.quiz_id AS quiz_result_id, " +
                        "       q.time_end AS taken_time, " +
                        "       c.name AS category_name, " +
                        "       CONCAT(u.firstname, ' ', u.lastname) AS full_name, " +
                        "       COUNT(DISTINCT qq.question_id) AS num_questions, " +
                        "       SUM(CASE WHEN ch.is_correct THEN 1 ELSE 0 END) AS score " +
                        "FROM Quiz q " +
                        "JOIN Category c ON q.category_id = c.category_id " +
                        "JOIN User u ON q.user_id = u.user_id " +
                        "LEFT JOIN QuizQuestion qq ON q.quiz_id = qq.quiz_id " +
                        "LEFT JOIN Choice ch ON qq.user_choice_id = ch.choice_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND q.category_id = ? ");
            params.add(categoryId);
        }

        if (userId != null) {
            sql.append(" AND q.user_id = ? ");
            params.add(userId);
        }

        sql.append(" GROUP BY q.quiz_id, q.time_end, c.name, u.firstname, u.lastname ");
        sql.append(" ORDER BY q.time_end DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new QuizResultSummary(
                rs.getInt("quiz_result_id"),
                rs.getTimestamp("taken_time"),
                rs.getString("category_name"),
                rs.getString("full_name"),
                rs.getInt("num_questions"),
                rs.getInt("score")
        ));
    }


}
