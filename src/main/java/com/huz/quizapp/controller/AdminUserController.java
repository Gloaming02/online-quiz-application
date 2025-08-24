package com.huz.quizapp.controller;

import com.huz.quizapp.domain.User;
import com.huz.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    private static final int PAGE_SIZE = 5;

    @GetMapping
    public String showUserManagement(@RequestParam(defaultValue = "1") int page, Model model) {
        List<User> users = userService.getUsersByPage(page, PAGE_SIZE);
        int totalUsers = userService.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin_user_management";
    }

    @PostMapping("/toggle/{userId}")
    public String toggleUserStatus(@PathVariable int userId, @RequestParam int currentPage) {
        userService.toggleUserStatus(userId);
        return "redirect:/admin/users?page=" + currentPage;
    }
}
