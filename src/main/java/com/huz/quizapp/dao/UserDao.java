package com.huz.quizapp.dao;

import com.huz.quizapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(User user) {
        String sql = "INSERT INTO user (email, password, firstname, lastname, is_active, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.isActive(),
                user.isAdmin());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setFirstname(rs.getString("firstname"));
            u.setLastname(rs.getString("lastname"));
            u.setActive(rs.getBoolean("is_active"));
            u.setAdmin(rs.getBoolean("is_admin"));
            return u;
        }, email, password);

        return users.isEmpty() ? null : users.get(0);
    }


    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password")); // 加密后的密码
            u.setFirstname(rs.getString("firstname"));
            u.setLastname(rs.getString("lastname"));
            u.setActive(rs.getBoolean("is_active"));
            u.setAdmin(rs.getBoolean("is_admin"));
            return u;
        }, email);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> findUsersWithPagination(int offset, int limit) {
        String sql = "SELECT * FROM user ORDER BY user_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{limit, offset}, (rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setEmail(rs.getString("email"));
            u.setFirstname(rs.getString("firstname"));
            u.setLastname(rs.getString("lastname"));
            u.setActive(rs.getBoolean("is_active"));
            u.setAdmin(rs.getBoolean("is_admin"));
            return u;
        });
    }

    public int countAllUsers() {
        String sql = "SELECT COUNT(*) FROM user";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void updateUserStatus(int userId, boolean isActive) {
        String sql = "UPDATE user SET is_active = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, isActive, userId);
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setEmail(rs.getString("email"));
            u.setFirstname(rs.getString("firstname"));
            u.setLastname(rs.getString("lastname"));
            u.setActive(rs.getBoolean("is_active"));
            u.setAdmin(rs.getBoolean("is_admin"));
            return u;
        });
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("is_active"));
            user.setAdmin(rs.getBoolean("is_admin"));
            return user;
        });
    }

}
