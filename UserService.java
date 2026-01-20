package com.diplav.blog.service;

import com.diplav.blog.entity.User;

public interface UserService {
    void register(User user);
    User login(String email, String password);
}
