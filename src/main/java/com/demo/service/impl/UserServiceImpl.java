package com.demo.service.impl;

import com.demo.base.UserType;
import com.demo.entity.User;
import com.demo.repo.UserRepo;
import com.demo.service.UserService;
import com.demo.util.RecordBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsers() {
        return userRepo.countUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User create(String email, String password, UserType userType) {
        User user = RecordBuilder.buildUser(email);
        user.setPassword(password);
        user.setUserType(userType);
        return create(user);
    }

    @Override
    public User create(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepo.save(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @PreAuthorize("#user.username ne authentication.principal.username")
    public void remove(User user) {
        userRepo.delete(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @PreAuthorize("authentication.principal.username ne @userServiceImpl.findById(#id).username")
    public void remove(Long id) {
        userRepo.delete(id);
    }
}
