package com.huz.quizapp.dao;

import com.huz.quizapp.domain.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question q = new Question();
        q.setQuestionId(rs.getInt("question_id"));
        q.setCategoryId(rs.getInt("category_id"));
        q.setDescription(rs.getString("description"));
        q.setActive(rs.getBoolean("is_active"));
        return q;
    }
}
