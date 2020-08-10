package com.teachme.teachme.controller;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.repository.UserDao;

import com.teachme.teachme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //@Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<DAOUser> getActualUser() {
        return ResponseEntity.ok(userService.getUserWithAuthorities().get());
    }

    @GetMapping("/adminpanel")
    public String getPanel() {
        return "This is Secured Resource";
    }
}
