package com.teachme.teachme.controller;

import com.teachme.teachme.DTO.SkillWrapper;
import com.teachme.teachme.service.UserSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserSkillController {

    private UserSkillService userSkillService;
    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }


    @GetMapping("/skills")
    public ResponseEntity skillsOfUser()
    {
        //return ResponseEntity.ok(userSkillService.showSkills(), HttpStatus.ACCEPTED);
        return new ResponseEntity(userSkillService.showSkills(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/skills")
    public ResponseEntity<?> addSkillstoUser(@Valid @RequestBody SkillWrapper skillWrapper)
    {
        return new ResponseEntity(userSkillService.addSkillstoUser(skillWrapper),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/skills")
    public ResponseEntity deleteSkillsofUser(@Valid @RequestBody SkillWrapper skillWrapper)
    {
        return new ResponseEntity(userSkillService.deleteSkillsofUser(skillWrapper),HttpStatus.ACCEPTED);
    }



}
