package com.huz.quizapp.service;

import com.huz.quizapp.dao.CategoryDao;
import com.huz.quizapp.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }
    public Category getCategoryById(int categoryId) {
        return categoryDao.findById(categoryId);
    }
}
