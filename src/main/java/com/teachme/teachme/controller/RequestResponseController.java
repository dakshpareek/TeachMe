package com.teachme.teachme.controller;

import com.teachme.teachme.service.RequestResponseService;
import com.teachme.teachme.dto.RequestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping( "/api/user" )
public class RequestResponseController {

    private RequestResponseService requestResponseService;

    public RequestResponseController( RequestResponseService requestResponseService ){

        this.requestResponseService = requestResponseService;
    }

    @GetMapping( "/request/response" )
    public ResponseEntity getAllRequestResponse(){

        return new ResponseEntity( requestResponseService.getallrequestresponse(), HttpStatus.OK );
    }

    @PostMapping( "/request/{request_id}/response" )
    public ResponseEntity createRequestResponse( int request_id , @Valid @RequestBody RequestResponseDTO requestResponseDTO ){

        return requestResponseService.createrequestresponse( request_id, requestResponseDTO );
    }

    @PostMapping( "/request/response/{response_id}" )
    public ResponseEntity updateRequestResponse( int response_id, @Valid @RequestBody RequestResponseDTO requestResponseDTO ){

        return requestResponseService.updaterequestresponse( response_id, requestResponseDTO );
    }

    @DeleteMapping( "/request/response/{response_id}" )
    public ResponseEntity deleteRequestResponse( int response_id ){

        return requestResponseService.deleterequestresponse( response_id );
    }

    @PutMapping( "/request/response/{response_id}" )
    public ResponseEntity changeStatusRequestResponse( int response_id ){

        return requestResponseService.changestatusrequestresponse( response_id );
    }

}
