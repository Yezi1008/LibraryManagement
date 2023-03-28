package com.ProjectOne.service;

import com.ProjectOne.model.User;

public interface UserService {
    boolean checkLogin(User user,String password);
    User findByUsername(String username);

    boolean updateuse(User user);
}
