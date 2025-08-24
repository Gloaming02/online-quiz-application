package com.huz.quizapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM User";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public String findMostPopularCategory() {
        String sql = "SELECT c.name " +
                "FROM Category c " +
                "JOIN Quiz q ON c.category_id = q.category_id " +
                "GROUP BY c.name " +
                "ORDER BY COUNT(*) DESC " +
                "LIMIT 1";

        List<String> results = jdbcTemplate.queryForList(sql, String.class);
        return results.isEmpty() ? null : results.get(0);
    }


}
