package com.diplav.blog.controller;

import com.diplav.blog.entity.User;
import com.diplav.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage() {
        return "login-form";
    }

    @PostMapping
    public String doLogin(@RequestParam String email, @RequestParam String password,
                          HttpServletRequest request, Model model) {
        try {
            User user = userService.login(email, password);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null, List.of());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            return "redirect:/api/v1/posts/allPosts";
        }
        catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "login-form";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
