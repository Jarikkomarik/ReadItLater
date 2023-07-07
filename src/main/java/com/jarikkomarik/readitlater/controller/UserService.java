package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.model.User;
import com.jarikkomarik.readitlater.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserService(UserRepository userRepository) {

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
