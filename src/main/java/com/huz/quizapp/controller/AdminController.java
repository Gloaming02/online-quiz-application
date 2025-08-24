package com.huz.quizapp.controller;

import com.huz.quizapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/home")
    public String showAdminHome(Model model) {
        model.addAttribute("userCount", adminService.getUserCount());
        model.addAttribute("mostPopularCategory", adminService.getMostPopularCategory());
        return "admin_home";
    }
}
