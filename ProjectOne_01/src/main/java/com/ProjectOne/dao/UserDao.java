package com.ProjectOne.dao;

import com.ProjectOne.model.User;

public interface UserDao {
    User findByUsername(String username);

    boolean updateuse(User user);
}
