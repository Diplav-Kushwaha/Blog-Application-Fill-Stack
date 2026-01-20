package com.diplav.blog.controller;

import com.diplav.blog.entity.User;
import com.diplav.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.register(user);
            return "redirect:/login";
        }
        catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registration-form";
        }
    }
}
