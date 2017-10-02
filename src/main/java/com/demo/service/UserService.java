package com.demo.service;

import com.demo.base.UserType;
import com.demo.entity.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    long countUsers();

    User create(String email, String password, UserType userType);

    User create(User user);

    void remove(User user);

    void remove(Long id);

    List<User> findAll();
}
