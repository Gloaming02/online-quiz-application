package com.huz.quizapp.dao;

import com.huz.quizapp.domain.Choice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChoiceRowMapper implements RowMapper<Choice> {
    @Override
    public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Choice choice = new Choice();
        choice.setChoiceId(rs.getInt("choice_id"));
        choice.setQuestionId(rs.getInt("question_id"));
        choice.setDescription(rs.getString("description"));
        choice.setCorrect(rs.getBoolean("is_correct"));
        return choice;
    }
}
