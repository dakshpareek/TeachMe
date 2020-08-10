package com.teachme.teachme.service;

import com.teachme.teachme.dto.CreateRequestContractDTO;
import com.teachme.teachme.dto.UpdateRequestContractDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestContract;
import com.teachme.teachme.repository.RequestContractRepository;
import com.teachme.teachme.repository.RequestRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestContractService {

    RequestRepository requestRepository;

    RequestContractRepository requestContractRepository;

    UserDao userRepository;

    public RequestContractService( RequestRepository requestRepository, RequestContractRepository requestContractRepository,
                                   UserDao userRepository ){

        this.requestRepository = requestRepository;
        this.requestContractRepository = requestContractRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createrequestcontract(int request_id, CreateRequestContractDTO createRequestContractDTO){

        Optional< Request > requestOptional = requestRepository.findById( request_id );

        if( requestOptional.isEmpty() ){

            return new ResponseEntity<>( "No such request exsists", HttpStatus.BAD_REQUEST );
        }

        Request request = requestOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser requestowner = request.getUser();

        if( requestowner.getId() != student.getId() ){

            return new ResponseEntity<>( "User not valid to create the contract", HttpStatus.BAD_REQUEST );
        }

        Optional<RequestContract> requestContractOptional = requestContractRepository.findByRequest( request );

        if( requestContractOptional.isPresent() ){

            return new ResponseEntity<>( "Contract for this request already created", HttpStatus.BAD_REQUEST );
        }

        userOptional = userRepository.findById( createRequestContractDTO.getTeacher_id() );

        if( userOptional.isEmpty() ){

            return new ResponseEntity<>( "Teacher does not exsist", HttpStatus.BAD_REQUEST );
        }

        DAOUser teacher = userOptional.get();

        RequestContract requestContract = new RequestContract();
        requestContract.setStudent( student );
        requestContract.setTeacher( teacher );
        requestContract.setRequest( request );
        requestContract.setHourlyPricing( createRequestContractDTO.isHourlyPricing() );
        requestContract.setPrice( createRequestContractDTO.getPrice() );

        requestContractRepository.save( requestContract );
        return new ResponseEntity<>( "Contract created successfully", HttpStatus.OK );
    }

    public ResponseEntity<?> getallrequestcontractsforstudents(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        List<RequestContract> requestContractList = requestContractRepository.findAllByStudent( student );

        return new ResponseEntity<>( requestContractList, HttpStatus.OK );
    }

    public ResponseEntity<?> getallrequestcontractsforteacher(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        List<RequestContract> requestContractList = requestContractRepository.findAllByTeacher( teacher );

        return new ResponseEntity<>( requestContractList, HttpStatus.OK );
    }

    public ResponseEntity<?> updaterequestcontract( long contract_id, UpdateRequestContractDTO updateRequestContractDTO ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            return new ResponseEntity<>( "Contract does not exsist", HttpStatus.BAD_REQUEST );
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser contractowner = requestContract.getStudent();

        if( student.getId() != contractowner.getId() ){

            return new ResponseEntity<>( "Student not valid to update contract details", HttpStatus.BAD_REQUEST );
        }

        requestContract.setHourlyPricing( updateRequestContractDTO.isHourlyPricing() );
        requestContract.setPrice( updateRequestContractDTO.getPrice() );
        requestContractRepository.save( requestContract );

        return new ResponseEntity<>( "Contract Updated Successfully", HttpStatus.OK );
    }

    public ResponseEntity<?> deleterequestcontract( long contract_id ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            return new ResponseEntity<>( "Contract does not exsist", HttpStatus.BAD_REQUEST );
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser contractowner = requestContract.getStudent();

        if( student.getId() != contractowner.getId() ){

            return new ResponseEntity<>( "Student not valid to delete contract", HttpStatus.BAD_REQUEST );
        }

        requestContractRepository.deleteById( requestContract.getId() );
        return new ResponseEntity<>( "Contract deleted successfully", HttpStatus.OK );
    }

    public ResponseEntity<?> changerequestcontractstatus( long contract_id ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            return new ResponseEntity<>( "Contract does not exsist", HttpStatus.BAD_REQUEST );
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        DAOUser contractacceptor = requestContract.getTeacher();

        if( teacher.getId() != contractacceptor.getId() ){

            return new ResponseEntity<>( "Teacher not valid to accept this contract", HttpStatus.BAD_REQUEST );
        }

        requestContract.setAccepted( !requestContract.isAccepted() );
        requestContractRepository.save( requestContract );
        return new ResponseEntity<>( "Status of contract changed successfully", HttpStatus.BAD_REQUEST );
    }

}
