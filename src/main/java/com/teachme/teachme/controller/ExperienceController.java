package com.teachme.teachme.controller;

import com.teachme.teachme.dto.ExperienceDTO;
import com.teachme.teachme.service.ExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ExperienceController {

    private ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/experiences")
    public ResponseEntity getAllExperiences()
    {
        return new ResponseEntity(experienceService.getAllExperience(), HttpStatus.OK);
    }

    @PostMapping("/experiences")
    public ResponseEntity createExperience(@Valid @RequestBody ExperienceDTO experienceDTO)
    {
        return new ResponseEntity(experienceService.createExperience(experienceDTO), HttpStatus.OK);
    }

    @GetMapping("/experiences/{id}")
    public ResponseEntity getExperience(@PathVariable long id)
    {
        return new ResponseEntity(experienceService.getExperience(id), HttpStatus.OK);
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity deleteExperience(@PathVariable long id)
    {
        return new ResponseEntity(experienceService.deleteExperience(id), HttpStatus.OK);
    }

}
