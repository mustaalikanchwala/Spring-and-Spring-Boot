package com.example.SpringJPA;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserClassSpringBoot createUser(@RequestBody UserClassSpringBoot user){
        return userService.createUser(user);
    }
    @GetMapping
    public List<UserClassSpringBoot> getUser(){
        return userService.getUser();
    }
}
