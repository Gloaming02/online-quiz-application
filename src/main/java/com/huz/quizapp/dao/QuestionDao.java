package com.huz.quizapp.dao;

import com.huz.quizapp.domain.Choice;
import com.huz.quizapp.domain.Question;
import com.huz.quizapp.domain.QuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Question> findRandomQuestionsByCategory(int categoryId, int limit) {
        String sql = "SELECT * FROM Question WHERE category_id = ? ORDER BY RAND() LIMIT ?";
        return jdbcTemplate.query(sql, new QuestionRowMapper(), categoryId, limit);
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new ChoiceRowMapper(), questionId);
    }

    public Question getQuestion() {
        String sql = "SELECT * FROM Question LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new QuestionRowMapper());
    }

    public List<Question> getQuestionsPaginated(int offset, int limit) {
        String sql = "SELECT * FROM Question ORDER BY question_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new QuestionRowMapper(), limit, offset);
    }

    public int getTotalQuestionCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Question", Integer.class);
    }

    public void toggleActive(int questionId) {
        String sql = "UPDATE Question SET is_active = NOT is_active WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }



    public List<QuestionInfo> findAllWithCategory(int offset, int pageSize) {
        String sql = "SELECT q.question_id, q.description, q.is_active, q.category_id, c.name AS category_name " +
                "FROM Question q " +
                "JOIN Category c ON q.category_id = c.category_id " +
                "ORDER BY q.question_id " +
                "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new QuestionInfo(
                rs.getInt("question_id"),
                rs.getString("description"),
                rs.getBoolean("is_active"),
                rs.getInt("category_id"),
                rs.getString("category_name"),
                null
        ), pageSize, offset);
    }


}
