package com.diplav.blog.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticated(String email, String password);
}
