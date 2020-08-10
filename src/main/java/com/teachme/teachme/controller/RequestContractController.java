package com.teachme.teachme.controller;

import com.teachme.teachme.service.RequestContractService;
import com.teachme.teachme.dto.CreateRequestContractDTO;
import com.teachme.teachme.dto.UpdateRequestContractDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestContractController {

    RequestContractService requestContractService;

    public RequestContractController(RequestContractService requestContractService ){

        this.requestContractService = requestContractService;
    }

    @PostMapping( "/api/user/request/{request_id}/contract" )
    public ResponseEntity<?> createRequestContract(@PathVariable int request_id, @RequestBody CreateRequestContractDTO createRequestContractDTO){

        return requestContractService.createrequestcontract( request_id, createRequestContractDTO);
    }

    @GetMapping( "/api/user/student/contract" )
    public ResponseEntity<?> getAllContractsForStudent(){

        return requestContractService.getallrequestcontractsforstudents();
    }

    @GetMapping( "/api/user/teacher/contract" )
    public ResponseEntity<?> getAllContractsForTeacher(){

        return requestContractService.getallrequestcontractsforteacher();
    }

    @PostMapping( "/api/user/contract/{contract_id}" )
    public ResponseEntity<?> updateContract(@PathVariable long contract_id, @RequestBody UpdateRequestContractDTO updateRequestContractDTO){

        return requestContractService.updaterequestcontract( contract_id, updateRequestContractDTO );
    }

    @DeleteMapping( "/api/user/contract/{contract_id}" )
    public ResponseEntity<?> deleteContract( @PathVariable long contract_id ){

        return requestContractService.deleterequestcontract( contract_id );
    }

    @PutMapping( "/api/user/contract/{contract_id}" )
    public ResponseEntity<?> changeContractStatus( long contract_id ){

        return requestContractService.changerequestcontractstatus( contract_id );
    }

}
