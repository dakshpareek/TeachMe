package com.teachme.teachme.controller;

import com.teachme.teachme.dto.CourseResponseDTO;
import com.teachme.teachme.service.CourseResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/user/courses")
@Validated
public class CourseResponseController {

    private CourseResponseService courseResponseService;

    public CourseResponseController(CourseResponseService courseResponseService) {
        this.courseResponseService = courseResponseService;
    }

    @GetMapping("/responses")
    public ResponseEntity getAllResponses()
    {
        log.info("In getAllResponses");

        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.getAllResponses(), HttpStatus.OK);

        log.info("Exiting getAllResponses");
        return responseEntity;
    }

    @GetMapping("/responses/{id}")
    public ResponseEntity getResponses(@PathVariable long id)
    {
        log.info("In getResponses");

        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.getResponses(id), HttpStatus.OK);

        log.info("Exiting getResponses");
        return responseEntity;
    }

    @DeleteMapping("/responses/{id}")
    public ResponseEntity deleteResponse(@PathVariable long id)
    {
        log.info("In deleteResponse");

        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.deleteResponse(id), HttpStatus.OK);

        log.info("Exiting deleteResponse");
        return responseEntity;
    }

    @PostMapping("{id}/responses")
    public ResponseEntity createResponse(@Valid @RequestBody CourseResponseDTO courseResponseDTO,@PathVariable long id)
    {
        log.info("In createResponse");

        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.createResponse(courseResponseDTO,id), HttpStatus.OK);

        log.info("Exiting createResponse");
        return responseEntity;
    }

    //This will be used by course owner to change status of a course response
    @PatchMapping( "/{id}/responses/{rid}")
    public ResponseEntity<String> changeStatus( @PathVariable long id, @PathVariable long rid)
    {
        log.info("In changeStatus");

        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.changeStatus(id,rid), HttpStatus.OK);

        log.info("In changeStatus");
        return responseEntity;
    }

    @GetMapping("/{id}/responses")
    public ResponseEntity getResponsesOnCourse(@PathVariable long id,@RequestParam(name="status",defaultValue = "-1") int status)
    {
        log.info("In getResponsesOnCourse");



        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.getResponsesOnCourse(id,status), HttpStatus.OK);

        log.info("In getResponsesOnCourse");
        return responseEntity;
    }


    @GetMapping("/{id}/responses/{rid}")
    public ResponseEntity getResponseOnCourseById(@PathVariable long id,@PathVariable long rid)
    {
        log.info("In getResponseOnCourseById");
        ResponseEntity responseEntity = new ResponseEntity(courseResponseService.getResponseOnCourseById(id,rid), HttpStatus.OK);
        log.info("In getResponseOnCourseById");
        return responseEntity;
    }

}
