package com.teachme.teachme.controller;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.UserDao;

import com.teachme.teachme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    //@Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/user")
    public ResponseEntity<DAOUser> getActualUser() {

        Optional<DAOUser> user = userService.getUserWithAuthorities();
        if (user.isEmpty())
        {
            throw new CustomException("User Not Found", HttpStatus.NOT_FOUND,"/user");
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/user")
    public ResponseEntity removeUser()
    {
        userService.removeUser();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","User Removed");
        body.put("status",200);
        body.put("path","/user");
        return new ResponseEntity(body,HttpStatus.OK);
    }

    @GetMapping("/adminpanel")
    public String getPanel() {
        return "This is Secured Resource";
    }


}
