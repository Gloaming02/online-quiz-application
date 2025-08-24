package com.huz.quizapp.service;

import com.huz.quizapp.dao.UserDao;
import com.huz.quizapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jasypt.util.text.AES256TextEncryptor;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    private final AES256TextEncryptor encryptor;

    public UserService() {
        this.encryptor = new AES256TextEncryptor();
        this.encryptor.setPassword("quiz-secret-key");
    }

    public boolean register(User user, boolean isActive, boolean isAdmin) {
        if (userDao.emailExists(user.getEmail())) return false;

        user.setPassword(encryptor.encrypt(user.getPassword()));
        user.setActive(isActive);
        user.setAdmin(isAdmin);

        userDao.registerUser(user);
        return true;
    }


    public User login(String email, String rawPassword) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            try {
                String decryptedPassword = encryptor.decrypt(user.getPassword());
                if (decryptedPassword.equals(rawPassword)) {
                    return user;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void registerAdmin(User user) {
        user.setPassword(encryptor.encrypt(user.getPassword()));
        user.setActive(true);
        user.setAdmin(true);
        userDao.registerUser(user);
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public List<User> getUsersByPage(int page, int size) {
        int offset = (page - 1) * size;
        return userDao.findUsersWithPagination(offset, size);
    }

    public int getTotalUserCount() {
        return userDao.countAllUsers();
    }

    public void toggleUserStatus(int userId) {
        User user = userDao.findById(userId);
        if (user != null) {
            user.setActive(!user.isActive());
            userDao.updateUserStatus(userId, user.isActive());
        }
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
