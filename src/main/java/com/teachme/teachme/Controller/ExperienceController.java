package com.teachme.teachme.controller;

import com.teachme.teachme.DTO.ExperienceDTO;
import com.teachme.teachme.DTO.ExperienceUpdateDTO;
import com.teachme.teachme.DTO.SkillWrapper;
import com.teachme.teachme.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
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

    @PutMapping("/experiences/{id}")
    public ResponseEntity updateExperience(@PathVariable long id,@Valid @RequestBody ExperienceUpdateDTO experienceUpdateDTO)
    {
        log.info("In updateExperience");
        ResponseEntity responseEntity = new ResponseEntity(experienceService.updateExperience(id,experienceUpdateDTO),HttpStatus.OK);
        log.info("Exiting From updateExperience");
        return responseEntity;
    }

    @PostMapping("/experiences/{id}/skills")
    public ResponseEntity addSkillsToExperience(@PathVariable long id,@Valid @RequestBody SkillWrapper skillWrapper)
    {
        log.info("In addSkillsToExperience");
        return new ResponseEntity(experienceService.addSkillsToExperience(id,skillWrapper), HttpStatus.OK);
    }


    @DeleteMapping("/experiences/{id}/skills")
    public ResponseEntity removeSkillsToExperience(@PathVariable long id,@Valid @RequestBody SkillWrapper skillWrapper)
    {
        log.info("In removeSkillsToExperience");
        ResponseEntity responseEntity = new ResponseEntity(experienceService.removeSkillsToExperience(id, skillWrapper), HttpStatus.OK);
        log.info("Exiting From removeSkillsToExperience");
        return responseEntity;
    }



}
