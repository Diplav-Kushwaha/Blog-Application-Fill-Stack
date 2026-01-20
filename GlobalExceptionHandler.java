package com.diplav.blog.config;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 : BAD REQUEST
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {

        model.addAttribute("errorCode", 400);
        model.addAttribute("errorTitle", "Bad Request");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/400";
    }

    //409 : CONFLICT
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException ex, Model model) {

        model.addAttribute("errorCode", 409);
        model.addAttribute("errorTitle", "Conflict");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/409";
    }

    //403 : FORBIDDEN
    @ExceptionHandler(SecurityException.class)
    public String handleSecurityException(SecurityException ex, Model model) {

        model.addAttribute("errorCode", 403);
        model.addAttribute("errorTitle", "Access Denied");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/403";
    }

    // 401 : UNAUTHORIZED
    @ExceptionHandler({RuntimeException.class, UsernameNotFoundException.class})
    public String handleUnauthorizedException(Exception ex, Model model) {

        model.addAttribute("errorCode", 401);
        model.addAttribute("errorTitle", "Unauthorized");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/401";
    }

    //500 : INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {

        model.addAttribute("errorCode", 500);
        model.addAttribute("errorTitle", "Internal Server Error");
        model.addAttribute("errorMessage",
                "Something went wrong. Please try again later.");

        return "error/500";
    }
}
