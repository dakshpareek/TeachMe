package com.teachme.teachme.service;

import com.teachme.teachme.dto.CreateRequestContractDTO;
import com.teachme.teachme.dto.UpdateRequestContractDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestContract;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.RequestContractRepository;
import com.teachme.teachme.repository.RequestRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
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

    public Object createRequestContract(int request_id, CreateRequestContractDTO createRequestContractDTO){

        log.info( "In createRequestContract" );

        // check wheather that request exsist or not
        Optional< Request > requestOptional = requestRepository.findById( request_id );

        if( requestOptional.isEmpty() ){

            throw new CustomException( "No such request exsists" , HttpStatus.NOT_FOUND,"/");
        }

        Request request = requestOptional.get();

        // check whether that student has right to create contract for that request
        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser requestowner = request.getUser();

        if( requestowner.getId() != student.getId() ){

            throw new CustomException( "User not valid to create the contract" , HttpStatus.UNAUTHORIZED,"/");
        }

        // check whether contract for that request exsist or not
        Optional<RequestContract> requestContractOptional = requestContractRepository.findByRequest( request );

        if( requestContractOptional.isPresent() ){

            throw new CustomException( "Contract for this request already created" , HttpStatus.CONFLICT,"/");
        }

        // check whether that teacher to whom contract was assigned exsist or not.
        userOptional = userRepository.findById( createRequestContractDTO.getTeacher_id() );

        if( userOptional.isEmpty() ){

            throw new CustomException( "Teacher does not exsist" , HttpStatus.NOT_FOUND,"/");
        }

        DAOUser teacher = userOptional.get();

        RequestContract requestContract = new RequestContract();
        requestContract.setStudent( student );
        requestContract.setTeacher( teacher );
        requestContract.setRequest( request );
        requestContract.setHourlyPricing( createRequestContractDTO.isHourlyPricing() );
        requestContract.setPrice( createRequestContractDTO.getPrice() );

        requestContractRepository.save( requestContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Contract created successfully");
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object getallrequestcontractsforstudents(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        List<RequestContract> requestContractList = requestContractRepository.findAllByStudent( student );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Request Contracts", requestContractList );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object getallrequestcontractsforteacher(){

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        List<RequestContract> requestContractList = requestContractRepository.findAllByTeacher( teacher );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Request Contracts", requestContractList );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object updaterequestcontract( long contract_id, UpdateRequestContractDTO updateRequestContractDTO ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist" , HttpStatus.NOT_FOUND,"/");
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser contractowner = requestContract.getStudent();

        if( student.getId() != contractowner.getId() ){

            throw new CustomException( "Student not valid to update contract details" , HttpStatus.UNAUTHORIZED,"/");
        }

        requestContract.setHourlyPricing( updateRequestContractDTO.isHourlyPricing() );
        requestContract.setPrice( updateRequestContractDTO.getPrice() );
        requestContractRepository.save( requestContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Contract Updated Successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object deleterequestcontract( long contract_id ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist" , HttpStatus.NOT_FOUND,"/");
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser student = userOptional.get();

        DAOUser contractowner = requestContract.getStudent();

        if( student.getId() != contractowner.getId() ){

            throw new CustomException( "Student not valid to delete contract" , HttpStatus.UNAUTHORIZED,"/");
        }

        requestContractRepository.deleteById( requestContract.getId() );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Contract deleted successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

    public Object changerequestcontractstatus( long contract_id ){

        Optional<RequestContract> requestContractOptional = requestContractRepository.findById( contract_id );

        if( requestContractOptional.isEmpty() ){

            throw new CustomException( "Contract does not exsist" , HttpStatus.NOT_FOUND,"/");
        }

        RequestContract requestContract = requestContractOptional.get();

        String currentusername = SecurityUtils.getCurrentUsername().get();
        Optional<DAOUser> userOptional = userRepository.findByEmail( currentusername );
        DAOUser teacher = userOptional.get();

        DAOUser contractacceptor = requestContract.getTeacher();

        if( teacher.getId() != contractacceptor.getId() ){

            throw new CustomException( "Teacher not valid to accept this contract" , HttpStatus.UNAUTHORIZED,"/");
        }

        requestContract.setAccepted( !requestContract.isAccepted() );
        requestContractRepository.save( requestContract );

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Status of contract changed successfully" );
        body.put("status",200);
        body.put("path","/");

        return body;
    }

}
