package com.scm.scm.controller;


import com.scm.scm.entity.Users;
import com.scm.scm.respository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class Test {
    private final UserRepo userRepo;
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
    @GetMapping ("/userDetails")
    public List<Users> UsersPresent(){
        return userRepo.findAll();
    }
}
