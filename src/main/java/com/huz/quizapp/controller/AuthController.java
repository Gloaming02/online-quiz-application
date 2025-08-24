package com.huz.quizapp.controller;

import com.huz.quizapp.domain.User;
import com.huz.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(HttpServletRequest request, Model model) {
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setFirstname(request.getParameter("firstname"));
        user.setLastname(request.getParameter("lastname"));
        boolean success = userService.register(user,true,false);
        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }
    }

    //login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.login(email, password);

        if (user != null) {
            System.out.println("isActive: " + user.isActive());
            if (!user.isActive()) {
                model.addAttribute("error", "Your account has been suspended and cannot log in.");
                return "login";
            }

            request.getSession().setAttribute("user", user);
            if (user.isAdmin()) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
