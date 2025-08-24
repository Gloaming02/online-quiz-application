package com.huz.quizapp.dao;

import com.huz.quizapp.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Category(rs.getInt("category_id"), rs.getString("name"))
        );
    }

    public Category findById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new Category(rs.getInt("category_id"), rs.getString("name"))
                , categoryId);
    }

}
