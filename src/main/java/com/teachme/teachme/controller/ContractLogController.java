package com.teachme.teachme.controller;

import com.teachme.teachme.dto.ContractLogDTO;
import com.teachme.teachme.service.ContractLogService;
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
public class ContractLogController {

    private ContractLogService contractLogService;
    public ContractLogController(ContractLogService contractLogService) {
        this.contractLogService = contractLogService;
    }

    //Create Log
    @PostMapping("/contract/{id}/logs")
    public ResponseEntity createLogs(@Valid @RequestBody ContractLogDTO contractLogDTO,@PathVariable long id)
    {
        log.info("In createLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.createLogs(contractLogDTO,id), HttpStatus.CREATED);

        log.info("Exiting createLogs");
        return responseEntity;
    }


    //Update Log Status (Owner of Contract Will Update This Status)
    @PatchMapping( "/contract/{id}/logs/{log_id}")
    public ResponseEntity<String> changeStatus( @PathVariable long id, @PathVariable long log_id)
    {
        log.info("In changeStatus");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.changeStatus(id,log_id), HttpStatus.OK);

        log.info("In changeStatus");
        return responseEntity;
    }

    //Request for log modification(when teacher or student want this to be changed)
    @PatchMapping( "/contract/{id}/logs/{log_id}/update_request")
    public ResponseEntity<String> updateRequested( @PathVariable long id, @PathVariable long log_id)
    {
        log.info("In updateRequested");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.updateRequested(id,log_id), HttpStatus.OK);

        log.info("In updateRequested");
        return responseEntity;
    }


    @GetMapping( "/contract/{id}/logs/")
    public ResponseEntity<String> getAllLogs( @PathVariable long id,@RequestParam(name = "verified",defaultValue = "-1") int isVerified,
                                              @RequestParam(name = "update_request",defaultValue = "-1") int isRequested)
    {
        log.info("In getAllLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.getAllLogsFilter(id,isVerified,isRequested), HttpStatus.OK);

        log.info("In getAllLogs");

        return responseEntity;
    }


    @PutMapping("/contract/{id}/logs/{log_id}/")
    public ResponseEntity<String> updateLog(@PathVariable long id,@PathVariable long log_id,@Valid @RequestBody ContractLogDTO contractLogDTO)
    {
        log.info("In updateLog");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.updateLog(contractLogDTO,id,log_id), HttpStatus.CREATED);

        log.info("Exiting updateLog");
        return responseEntity;
    }

    /*
    * WRITE LOGIC OF DELETION OF LOG
    * */


    /*
    @GetMapping( "/contract/{id}/logs/update_request")
    public ResponseEntity<String> getAllRequestedLogs( @PathVariable long id,@RequestParam(name = "update_request",defaultValue = "0") int isRequested)
    {
        log.info("In getAllRequestedLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.getAllRequestedLogs(id,isRequested), HttpStatus.OK);

        log.info("In getAllRequestedLogs");

        return responseEntity;
    }

     */

}
