package com.teachme.teachme.Service;

import com.teachme.teachme.dto.RequestDTO;
import com.teachme.teachme.dto.RequestResponseDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestResponse;
import com.teachme.teachme.repository.RequestRepository;
import com.teachme.teachme.repository.RequestResponseRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RequestService {

    private UserDao userRepository;

    private RequestRepository requestRepository;

    public RequestService( UserDao userDao, RequestRepository requestRepository ){

        this.userRepository = userDao;
        this.requestRepository = requestRepository;
    }

    public List<Request> getallrequests(){

        String currentusername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        List<Request> allRequests = requestRepository.findAllByUser( user );
        return allRequests;
    }

    public ResponseEntity createrequest(RequestDTO requestDTO){

        String currentusername = SecurityUtils.getCurrentUsername().get();

        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser user = userOptional.get();

        Request request = new Request();

        request.setTitle( requestDTO.getTitle() );
        request.setDescription( requestDTO.getDescription() );
        request.setPublic( requestDTO.isPublic() );
        request.setOffered_price( requestDTO.getOffered_price() );
        request.setHourlyPrice( requestDTO.isHourlyPrice() );
        request.setClosed( requestDTO.isClosed() );
        request.setUser( user );
        request.setSkills( requestDTO.getSkillSet() );

        requestRepository.save( request );
        return new ResponseEntity( "Request created successfully", HttpStatus.OK );
    }

    public ResponseEntity updaterequest( int request_id, RequestDTO requestDTO ){

        Optional<Request> requestOptional = requestRepository.findById( request_id );
        Request request = requestOptional.get();

        request.setTitle( requestDTO.getTitle() );
        request.setDescription( requestDTO.getDescription() );
        request.setPublic( requestDTO.isPublic() );
        request.setOffered_price( requestDTO.getOffered_price() );
        request.setHourlyPrice( requestDTO.isHourlyPrice() );
        request.setClosed( requestDTO.isClosed() );
        request.setSkills( requestDTO.getSkillSet() );

        requestRepository.save( request );
        return new ResponseEntity( "Request updated successfully", HttpStatus.OK );
    }

}
