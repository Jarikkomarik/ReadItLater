package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.model.User;
import com.jarikkomarik.readitlater.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1")
public record UserController(UserService userService) {


    @GetMapping("users")
    public Flux<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("user")
    public Mono<User> getUsers(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
