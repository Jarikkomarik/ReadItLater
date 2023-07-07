package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public record UserController(UserService userService) {


    @GetMapping("users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("user")
    public User getUsers(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
