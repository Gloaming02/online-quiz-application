//
//package com.huz.quizapp.controller;
//
//import com.huz.quizapp.dao.QuizResultDao;
//import com.huz.quizapp.domain.*;
//import com.huz.quizapp.service.QuizService;
//import com.huz.quizapp.service.QuestionService;
//import com.huz.quizapp.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@Controller
//public class QuizAdminController {
//
//    @Autowired
//    private QuizResultDao quizResultDao;
//
//    @Autowired
//    private QuestionService questionService;
//
//    @GetMapping("/admin/quizzes")
//    public String showQuizResults(Model model) {
//        List<QuizResultSummary> results = quizResultDao.findAllSummaries();
//        model.addAttribute("results", results);
//        return "quiz_management";
//    }
//}

package com.huz.quizapp.controller;

import com.huz.quizapp.dao.QuizResultDao;
import com.huz.quizapp.domain.QuizResultSummary;
import com.huz.quizapp.service.CategoryService;
import com.huz.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuizAdminController {

    @Autowired
    private QuizResultDao quizResultDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private static final int PAGE_SIZE = 5;

    @GetMapping("/admin/quizzes")
    public String showQuizResults(@RequestParam(required = false) Integer categoryId,
                                  @RequestParam(required = false) Integer userId,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String sortBy,
                                  Model model) {

        List<QuizResultSummary> results = quizResultDao.findAllSummaries(categoryId, userId);

        if ("fullName".equals(sortBy)) {
            results.sort(Comparator.comparing(QuizResultSummary::getFullName, String.CASE_INSENSITIVE_ORDER));
        } else if ("categoryName".equals(sortBy)) {
            results.sort(Comparator.comparing(QuizResultSummary::getCategoryName, String.CASE_INSENSITIVE_ORDER));
        }

        int totalResults = results.size();
        int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);
        int fromIndex = (page - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, totalResults);
        List<QuizResultSummary> pageResults = results.subList(fromIndex, toIndex);

        model.addAttribute("results", pageResults);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedUser", userId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortBy", sortBy);

        return "quiz_management";
    }
}
