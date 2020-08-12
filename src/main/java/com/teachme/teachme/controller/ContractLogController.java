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
