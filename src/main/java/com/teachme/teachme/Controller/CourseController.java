package com.teachme.teachme.controller;

import com.teachme.teachme.DTO.*;
import com.teachme.teachme.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/user")
@Validated
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService)
    {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity getAllCourses()
    {
        log.info("In getAllCourses");

        ResponseEntity responseEntity = new ResponseEntity(courseService.getAllCourses(), HttpStatus.OK);

        log.info("Exiting getAllCourses");
        return responseEntity;
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity getCourses(@PathVariable long id)
    {
        log.info("In getCourses");

        ResponseEntity responseEntity = new ResponseEntity(courseService.getCourses(id), HttpStatus.OK);

        log.info("Exiting getCourses");
        return responseEntity;
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity deleteCourses(@PathVariable long id)
    {
        log.info("In deleteCourses");

        ResponseEntity responseEntity = new ResponseEntity(courseService.deleteCourses(id), HttpStatus.OK);

        log.info("Exiting deleteCourses");
        return responseEntity;
    }

    @PostMapping("/courses")
    public ResponseEntity createCourses(@Valid @RequestBody CourseDTO courseDTO)
    {
        log.info("In createCourses");

        ResponseEntity responseEntity = new ResponseEntity(courseService.createCourse(courseDTO), HttpStatus.OK);

        log.info("Exiting createCourses");
        return responseEntity;
    }


    @PutMapping("/courses/{id}")
    public ResponseEntity updateCourses(@PathVariable long id,@Valid @RequestBody CourseUpdateDTO courseUpdateDTO)
    {
        log.info("In Courses");
        ResponseEntity responseEntity = new ResponseEntity(courseService.updateCourses(id,courseUpdateDTO),HttpStatus.OK);
        log.info("Exiting From Courses");
        return responseEntity;
    }


    @PostMapping("/courses/{id}/skills")
    public ResponseEntity addSkillsToCourse(@PathVariable long id,@Valid @RequestBody SkillWrapper skillWrapper)
    {
        log.info("In addSkillsToCourse");

        ResponseEntity responseEntity = new ResponseEntity(courseService.addSkillsToCourse(id, skillWrapper), HttpStatus.OK);

        log.info("Exiting From removeSkillsToExperience");
        return responseEntity;
    }


    @DeleteMapping("/courses/{id}/skills")
    public ResponseEntity removeSkillsToCourse(@PathVariable long id,@Valid @RequestBody SkillWrapper skillWrapper)
    {
        log.info("In removeSkillsToCourse");

        ResponseEntity responseEntity = new ResponseEntity(courseService.removeSkillsToCourse(id, skillWrapper), HttpStatus.OK);

        log.info("Exiting From removeSkillsToCourse");
        return responseEntity;
    }

    @PatchMapping( "/courses/{id}/status")
    public ResponseEntity<String> changeStatus( @PathVariable long id )
    {
        log.info("In changeStatus");

        ResponseEntity responseEntity = new ResponseEntity(courseService.changeStatus(id), HttpStatus.OK);

        log.info("In changeStatus");
        return responseEntity;
    }

}
