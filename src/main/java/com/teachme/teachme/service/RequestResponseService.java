package com.teachme.teachme.service;

import com.teachme.teachme.dto.RequestResponseDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestResponse;
import com.teachme.teachme.repository.RequestRepository;
import com.teachme.teachme.repository.RequestResponseRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RequestResponseService {


    private UserDao userRepository;

    private RequestRepository requestRepository;

    private RequestResponseRepository requestResponseRepository;

    public RequestResponseService( UserDao userDao, RequestRepository requestRepository, RequestResponseRepository requestResponseRepository ){

        this.userRepository = userDao;
        this.requestRepository = requestRepository;
        this.requestResponseRepository = requestResponseRepository;
    }

    public List<RequestResponse> getallrequestresponse(){

        String currentusername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        List<RequestResponse> allrequestresponse = requestResponseRepository.findAllByUser( user );
        return allrequestresponse;
    }

    public ResponseEntity createrequestresponse(int request_id, RequestResponseDTO requestResponseDTO ){

        String currentusername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        Optional<Request> requestOptional = requestRepository.findById( request_id );
        Request request = requestOptional.get();
        Optional<RequestResponse> requestResponseOptional = requestResponseRepository.findByRequestAndUser( request , user );

        if( requestResponseOptional.isPresent() ){

            return new ResponseEntity( "You have already created response for this request", HttpStatus.OK );
        }

        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setRequest( request );
        requestResponse.setUser( user );
        requestResponse.setProposed_price( requestResponseDTO.getProposed_price() );
        requestResponse.setHourlyPrice( requestResponseDTO.isHourlyPrice() );
        requestResponse.setMessage( requestResponseDTO.getMessage() );

        Set<RequestResponse> requestResponseSet = request.getRequestResponseSet();
        requestResponseSet.add( requestResponse );
        request.setRequestResponseSet( requestResponseSet );

        requestResponseRepository.save( requestResponse );
        return new ResponseEntity( "Response created successfully", HttpStatus.OK );
    }

    public ResponseEntity updaterequestresponse( int response_id, RequestResponseDTO requestResponseDTO ){

        Optional<RequestResponse> requestResponseOptional = requestResponseRepository.findById( response_id );
        RequestResponse requestResponse = requestResponseOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        if( requestResponse.getUser().getId() != user.getId() ){

            return new ResponseEntity( "User not valid to update this response", HttpStatus.BAD_REQUEST );
        }

        requestResponse.setProposed_price( requestResponseDTO.getProposed_price() );
        requestResponse.setHourlyPrice( requestResponseDTO.isHourlyPrice() );
        requestResponse.setMessage( requestResponseDTO.getMessage() );

        requestResponseRepository.save( requestResponse );
        return new ResponseEntity( "Response updated successfully", HttpStatus.OK );

    }

    public ResponseEntity deleterequestresponse( int response_id ){

        Optional<RequestResponse> requestResponseOptional = requestResponseRepository.findById( response_id );
        RequestResponse requestResponse = requestResponseOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        if( requestResponse.getUser().getId() != user.getId() ){

            return new ResponseEntity( "User not valid to update this response", HttpStatus.BAD_REQUEST );
        }

        Request request = requestResponse.getRequest();

        Set<RequestResponse> requestResponseSet = request.getRequestResponseSet();
        requestResponseSet.remove( requestResponse );
        request.setRequestResponseSet( requestResponseSet );

        requestResponseRepository.deleteById( response_id );
        return new ResponseEntity( "Response deleted successfully", HttpStatus.OK );
    }

    public ResponseEntity changestatusrequestresponse( int response_id ){

        Optional<RequestResponse> requestResponseOptional = requestResponseRepository.findById( response_id );
        RequestResponse requestResponse = requestResponseOptional.get();

        Request request = requestResponse.getRequest();

        DAOUser requestowner = request.getUser();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        if( requestowner.getId() != user.getId() ){

            return new ResponseEntity( "User not valid to change status of this response", HttpStatus.BAD_REQUEST );
        }

        requestResponse.setAccepted( !requestResponse.isAccepted() );
        return new ResponseEntity( "Response status changed successfully", HttpStatus.OK );
    }

}
