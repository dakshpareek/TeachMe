package com.teachme.teachme.controller;

import com.teachme.teachme.dto.ContractLogDTO;
import com.teachme.teachme.dto.ContractLogUpdateDTO;
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

    //Create Log for request contract
    @PostMapping("/request/contract/{id}/logs")
    public ResponseEntity requestCreateLogs(@Valid @RequestBody ContractLogDTO contractLogDTO,@PathVariable long id)
    {
        log.info("In requestCreateLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.requestCreateLogs(contractLogDTO,id), HttpStatus.CREATED);

        log.info("Exiting requestCreateLogs");
        return responseEntity;
    }



    //Update Log Status (Owner of Contract Will Update This Status)
    @PatchMapping( "/request/contract/{contract_id}/logs/{log_id}")
    public ResponseEntity<String> requestChangeStatus( @PathVariable long contract_id, @PathVariable long log_id)
    {
        log.info("In requestChangeStatus");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.requestChangeStatus(contract_id,log_id), HttpStatus.OK);

        log.info("Exiting requestChangeStatus");
        return responseEntity;
    }

    //Request for log modification(when teacher or student want this to be changed)
    @PatchMapping( "/request/contract/{id}/logs/{log_id}/update_request")
    public ResponseEntity<String> requestUpdateRequested( @PathVariable long id, @PathVariable long log_id)
    {
        log.info("In requestUpdateRequested");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.requestUpdateRequested(id,log_id), HttpStatus.OK);

        log.info("Exiting requestUpdateRequested");
        return responseEntity;
    }


    @GetMapping( "/request/contract/{id}/logs")
    public ResponseEntity<String> requestGetAllLogs( @PathVariable long id,@RequestParam(name = "verified",defaultValue = "-1") int isVerified)
    {
        log.info("In requestGetAllLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.requestGetAllLogs(id,isVerified), HttpStatus.OK);

        log.info("Exiting requestGetAllLogs");

        return responseEntity;
    }


    @PutMapping("/request/contract/{contract_id}/logs/{log_id}")
    public ResponseEntity<String> requestUpdateLog(@PathVariable long contract_id,@PathVariable long log_id,@Valid @RequestBody ContractLogDTO contractLogDTO)
    {
        log.info("In requestUpdateLog");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.requestUpdateLog(contractLogDTO,contract_id,log_id), HttpStatus.CREATED);

        log.info("Exiting requestUpdateLog");
        return responseEntity;
    }

    /*
    * WRITE LOGIC OF DELETION OF LOG
    * */

    //Logic For Course Log


    @GetMapping( "/course/contract/{contract_id}/logs")
    public ResponseEntity<String> courseGetAllLogs( @PathVariable long contract_id,@RequestParam(name = "verified",defaultValue = "-1") int isVerified)
    {
        log.info("In courseGetAllLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.courseGetAllLogs(contract_id,isVerified), HttpStatus.OK);

        log.info("Exiting courseGetAllLogs");

        return responseEntity;
    }


    //Create Log for course contract
    @PostMapping("/course/contract/{contract_id}/logs")
    public ResponseEntity courseCreateLogs(@Valid @RequestBody ContractLogDTO contractLogDTO,@PathVariable long contract_id)
    {
        log.info("In courseCreateLogs");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.courseCreateLogs(contractLogDTO,contract_id), HttpStatus.CREATED);

        log.info("Exiting courseCreateLogs");
        return responseEntity;
    }

    //Update Log Status (Owner of Contract Will Update This Status)
    @PatchMapping( "/course/contract/{contract_id}/logs/{log_id}")
    public ResponseEntity<String> courseChangeStatus( @PathVariable long contract_id, @PathVariable long log_id)
    {
        log.info("In courseChangeStatus");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.courseChangeStatus(contract_id,log_id), HttpStatus.OK);

        log.info("Exiting courseChangeStatus");
        return responseEntity;
    }


    //Request for log modification(when teacher or student want this to be changed)
    @PatchMapping( "/course/contract/{contract_id}/logs/{log_id}/update_request")
    public ResponseEntity<String> courseUpdateRequested( @PathVariable long contract_id, @PathVariable long log_id)
    {
        log.info("In courseUpdateRequested");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.courseUpdateRequested(contract_id,log_id), HttpStatus.OK);

        log.info("Exiting courseUpdateRequested");
        return responseEntity;
    }

    @PutMapping("/course/contract/{contract_id}/logs/{log_id}")
    public ResponseEntity<String> courseUpdateLog(@PathVariable long contract_id,@PathVariable long log_id,@Valid @RequestBody ContractLogUpdateDTO contractLogDTO)
    {
        log.info("In courseUpdateLog");

        ResponseEntity responseEntity = new ResponseEntity(contractLogService.courseUpdateLog(contractLogDTO,contract_id,log_id), HttpStatus.OK);

        log.info("Exiting courseUpdateLog");
        return responseEntity;
    }

}
