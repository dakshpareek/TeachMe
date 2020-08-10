package com.teachme.teachme.controller;

import com.teachme.teachme.service.RequestService;
import com.teachme.teachme.dto.RequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping( "/api/user" )
public class RequestController {

    private RequestService requestService;

    public RequestController( RequestService requestService ){

        this.requestService = requestService;
    }

    @GetMapping( "/request" )
    public ResponseEntity getAllRequests(){

        return new ResponseEntity( requestService.getallrequests(), HttpStatus.OK );
    }

    @PostMapping( "/request" )
    public ResponseEntity createRequest( @Valid @RequestBody RequestDTO requestdto ){

        return requestService.createrequest( requestdto );
    }

    @PostMapping( "/request/{request_id}" )
    public ResponseEntity updateRequest( @PathVariable int request_id, @Valid @RequestBody RequestDTO requestDTO ){

        return requestService.updaterequest( request_id, requestDTO );
    }

    /*

    Code for delete request api
     */

}
