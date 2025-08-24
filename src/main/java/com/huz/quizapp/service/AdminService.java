package com.huz.quizapp.service;

import com.huz.quizapp.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public int getUserCount() {
        return adminDao.countUsers();
    }

    public String getMostPopularCategory() {
        return adminDao.findMostPopularCategory();
    }
}
