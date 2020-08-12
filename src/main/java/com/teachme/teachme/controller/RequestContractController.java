package com.teachme.teachme.controller;

import com.teachme.teachme.service.RequestContractService;
import com.teachme.teachme.dto.CreateRequestContractDTO;
import com.teachme.teachme.dto.UpdateRequestContractDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RequestContractController {

    RequestContractService requestContractService;

    public RequestContractController(RequestContractService requestContractService ){

        this.requestContractService = requestContractService;
    }

    @PostMapping( "/api/user/request/{request_id}/contract" )
    public ResponseEntity<?> createRequestContract(@PathVariable int request_id, @RequestBody CreateRequestContractDTO createRequestContractDTO){

        log.info("In createRequestContract");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.createRequestContract( request_id, createRequestContractDTO ) , HttpStatus.OK );

        log.info("Exiting createRequestContract");
        return responseEntity;
    }

    @GetMapping( "/api/user/student/contract/request" )
    public ResponseEntity<?> getAllRequestContractsForStudent(){

        log.info("In getAllRequestContractsForStudent");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.getallrequestcontractsforstudents() , HttpStatus.OK );

        log.info("Exiting getAllRequestContractsForStudent");
        return responseEntity;
    }

    @GetMapping( "/api/user/teacher/contract/request" )
    public ResponseEntity<?> getAllRequestContractsForTeacher(){

        log.info("In getAllRequestContractsForTeacher");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.getallrequestcontractsforteacher() , HttpStatus.OK );

        log.info("Exiting getAllRequestContractsForTeacher");
        return responseEntity;
    }

    @PostMapping( "/api/user/contract/request/{contract_id}" )
    public ResponseEntity<?> updateRequestContract(@PathVariable long contract_id, @RequestBody UpdateRequestContractDTO updateRequestContractDTO){

        log.info("In updateRequestContract");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.updaterequestcontract( contract_id, updateRequestContractDTO ) , HttpStatus.OK );

        log.info("Exiting updateRequestContract");
        return responseEntity;
    }

    @DeleteMapping( "/api/user/contract/request/{contract_id}" )
    public ResponseEntity<?> deleteRequestContract( @PathVariable long contract_id ){

        log.info("In deleteRequestContract");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.deleterequestcontract( contract_id ) , HttpStatus.OK );

        log.info("Exiting deleteRequestContract");
        return responseEntity;
    }

    @PutMapping( "/api/user/contract/request/{contract_id}" )
    public ResponseEntity<?> changeRequestContractStatus( long contract_id ){

        log.info("In changeRequestContractStatus");

        ResponseEntity responseEntity = new ResponseEntity( requestContractService.changerequestcontractstatus( contract_id ) , HttpStatus.OK );

        log.info("Exiting changeRequestContractStatus");
        return responseEntity;
    }

}
