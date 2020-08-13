package com.teachme.teachme.controller;

import com.teachme.teachme.dto.CreateCourseContractDTO;
import com.teachme.teachme.dto.CreateRequestContractDTO;
import com.teachme.teachme.dto.UpdateCourseContractDTO;
import com.teachme.teachme.dto.UpdateRequestContractDTO;
import com.teachme.teachme.service.CourseContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CourseContractController {

    CourseContractService courseContractService;

    public CourseContractController( CourseContractService courseContractService ){

        this.courseContractService = courseContractService;
    }

    @PostMapping( "/api/user/course/{course_id}/contract" )
    public ResponseEntity<?> createCourseContract(@PathVariable int course_id, @RequestBody CreateCourseContractDTO createCourseContractDTO ){

        log.info("In createCourseContract");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.createCourseContract( course_id, createCourseContractDTO ) , HttpStatus.OK );

        log.info("Exiting createCourseContract");
        return responseEntity;
    }

    @GetMapping( "/api/user/student/contract/course" )
    public ResponseEntity<?> getAllCourseContractsForStudent(){

        log.info("In getAllCourseContractsForStudent");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.getAllCourseContractForStudent(), HttpStatus.OK );

        log.info("Exiting getAllCourseContractsForStudent");
        return responseEntity;
    }

    @GetMapping( "/api/user/teacher/contract/course" )
    public ResponseEntity<?> getAllCourseContractsForTeacher(){

        log.info("In getAllCourseContractsForTeacher");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.getAllCourseContractForTeacher(), HttpStatus.OK );

        log.info("Exiting getAllCourseContractsForTeacher");
        return responseEntity;
    }

    @PostMapping( "/api/user/contract/course/{contract_id}" )
    public ResponseEntity<?> updateCourseContract(@PathVariable long contract_id, @RequestBody UpdateCourseContractDTO updateCourseContractDTO ){

        log.info("In updateCourseContract");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.updateCourseContract( contract_id, updateCourseContractDTO ), HttpStatus.OK );

        log.info("Exiting updateCourseContract");
        return responseEntity;
    }

    @DeleteMapping( "/api/user/contract/course/{contract_id}" )
    public ResponseEntity<?> deleteCourseContract( @PathVariable long contract_id ){

        log.info("In deleteCourseContract");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.deleteCourseContract( contract_id ), HttpStatus.OK );

        log.info("Exiting deleteCourseContract");
        return responseEntity;
    }

    @PutMapping( "/api/user/contract/course/{contract_id}" )
    public ResponseEntity<?> changeCourseContractStatus( long contract_id ){

        log.info("In changeCourseContractStatus");

        ResponseEntity responseEntity = new ResponseEntity( courseContractService.changeCourseContractStatus( contract_id ), HttpStatus.OK );

        log.info("Exiting changeCourseContractStatus");
        return responseEntity;
    }


}
