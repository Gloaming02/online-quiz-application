package com.huz.quizapp.controller;


import com.huz.quizapp.domain.Category;
import com.huz.quizapp.domain.Quiz;
import com.huz.quizapp.domain.User;
import com.huz.quizapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.huz.quizapp.service.QuizService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuizService quizService;


    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Quiz> recentQuizzes = quizService.getQuizzesByUser(user.getUserId());
        model.addAttribute("recentQuizzes", recentQuizzes);

        return "home";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

}
