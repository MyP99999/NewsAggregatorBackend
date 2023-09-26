package com.example.EvidenNewsAggregator.controllers;


import com.example.EvidenNewsAggregator.entities.tables.pojos.Users;
import com.example.EvidenNewsAggregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public Iterable<Users> findAll(){
        return userService.findAll();
    }
}
