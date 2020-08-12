package com.teachme.teachme.service;

import com.teachme.teachme.dto.ContractLogDTO;
import com.teachme.teachme.entity.ContractLogs;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.RequestContract;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.ContractLogRepository;
import com.teachme.teachme.repository.RequestContractRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
@Slf4j
public class ContractLogService {

    private ContractLogRepository contractLogRepository;
    private UserDao userRepository;
    private ContractLogs contractLogs;
    private RequestContractRepository requestContractRepository;
    public ContractLogService(ContractLogRepository contractLogRepository,
                              UserDao userRepository,RequestContractRepository requestContractRepository) {
        this.contractLogRepository = contractLogRepository;
        this.userRepository = userRepository;
        this.requestContractRepository = requestContractRepository;
    }

    public Object requestCreateLogs(ContractLogDTO contractLogDTO, long id) {
        log.info("In requestCreateLogs service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //check this contract exists and it belongs to this user (check for both end)

        boolean isOwner = false;
        Optional<RequestContract> contractOptional = requestContractRepository.findById(id);

        if(contractOptional.isEmpty())
        {
            log.info("Not A REQUEST contract");
            throw new CustomException("Contract Not Found", HttpStatus.NOT_FOUND,"/");

        }

        RequestContract contract = contractOptional.get();
        //now check if this belongs to this logged in user
        //check if this user is owner of this contract

        if(contract.getStudent() == user)
        {
            log.info("User is owner of this contract");
            isOwner = true;
        }
        else if(contract.getTeacher() == user)
        {
            log.info("User is not owner of this contract");
        }
        else
        {
            throw new CustomException("User Not Belong To This Contract",HttpStatus.BAD_REQUEST,"/");
        }



        //after all checks store this DTO
        ContractLogs contractLog = new ContractLogs();


        contractLog.setLogMessage(contractLogDTO.getLogMessage());
        contractLog.setCreatedDate(contractLogDTO.getCreatedDate());
        contractLog.setEndDate(contractLogDTO.getEndDate());

        //GET TIME DIFFERENCE AND STORE IN DURATION
        LocalDateTime tempDateTime = LocalDateTime.from( contractLog.getCreatedDate() );

        /*
        long hours = tempDateTime.until( log.getEndDate(), ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );

         */

        long minutes = tempDateTime.until( contractLog.getEndDate(), ChronoUnit.MINUTES );
        BigDecimal inMinutes = new BigDecimal(minutes);

        contractLog.setLectureDuration(inMinutes);


        //contractLog.setRequestContract(contract);


        if(isOwner)
        {
            //if this user is owner then set isVerified to true
            contractLog.setVerified(true);
        }

        contractLogRepository.save(contractLog);


        //save contract also
        //map log to contract also
        contract.addContract(contractLog);

        requestContractRepository.save(contract);

        log.info("Exiting requestCreateLogs service");
        return contractLog;

    }

    public Map<String, Object> requestChangeStatus(long contract_id, long log_id) {
        log.info("In changeStatus service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //Here we will check whether this user owns this contract or not
        //Check if this log is there and it is a requestContract
        //Optional<ContractLogs> contractLogsOptional = contractLogRepository.findByIdAndRequestContract(log_id, contract_id);
        Optional<ContractLogs> contractLogsOptional = contractLogRepository.findById(log_id);

        if(contractLogsOptional.isEmpty())
        {
            log.info("Log Not Found");
            throw new CustomException("Log Not Found", HttpStatus.NOT_FOUND,"/");
        }

        ContractLogs contractLog = contractLogsOptional.get();

        //check if this is a requestContract
        RequestContract requestContract = contractLog.getRequestContract();

        if(requestContract == null)
        {
            log.info("Not a request contract");
            throw new CustomException("Not a request contract", HttpStatus.BAD_REQUEST,"/");
        }

        if(requestContract.getStudent() == user)
        {
            contractLog.setVerified(true);
            log.info("User is owner of request");
        }
        else{
            //if not belong to anyone then throw exception
            throw new CustomException("Not authorized to change status", HttpStatus.BAD_REQUEST,"/");
        }



        contractLogRepository.save(contractLog);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Successfully Changed Status Of Log");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting changeStatus service");
        return body;
    }

    public Object requestUpdateRequested(long contract_id, long log_id) {
        log.info("In requestUpdateRequested service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //Here we will check whether this user belongs to this contract or not (this user is not owner of contract)
        //Optional<RequestContract> requestContract = requestContractRepository.findByIdAndStudent(contract_id, user);
        Optional<RequestContract> requestContract = requestContractRepository.findById(contract_id);


        if(requestContract.isEmpty())
        {
            log.info("Contract not found or not authorized to modify log");
            throw new CustomException("Contract not found or not authorized to modify log", HttpStatus.NOT_FOUND,"/");
        }

        //check if this is owner then skip this because he can not ask for change in log
        if (requestContract.get().getStudent() == user)
        {
            log.info("Not authorized to request modify log");
            throw new CustomException("Not authorized to request modify log", HttpStatus.BAD_REQUEST,"/");
        }

        Optional<ContractLogs> contractLogsOptional = contractLogRepository.findById(log_id);

        if(contractLogsOptional.isEmpty())
        {
            log.info("Log not found");
            throw new CustomException("Log not found", HttpStatus.NOT_FOUND,"/");
        }
        ContractLogs contractLog = contractLogsOptional.get();
        //request for update
        contractLog.setUpdateRequested(true);

        //save this contract now
        contractLogRepository.save(contractLog);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Successfully Requested for modification");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting requestUpdateRequested service");
        return body;
    }

    public Object getAllLogs(long id, int isVerified) {
        log.info("In getAllLogs service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get all logs of this contract if this belongs to user

        List<ContractLogs> contractLogs;
        //based on isVerified get logs
        if(isVerified == -1)
        {
            contractLogs = contractLogRepository.findAllById(id);
        }
        else
        {
            contractLogs = contractLogRepository.findAllByIdAndIsVerified(id,isVerified > 0);
        }



        log.info("Exiting getAllLogs service");

        return contractLogs;

    }

    public Object requestGetAllLogs(long id, int isVerified) {
        log.info("In requestGetAllLogs service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get all logs of this contract if this belongs to user
        Optional<RequestContract> contractOptional = requestContractRepository.findById(id);
        if(contractOptional.isEmpty())
        {
            throw new CustomException("Contract Not Found",HttpStatus.NOT_FOUND,"/");
        }

        RequestContract contract = contractOptional.get();
        //check if this user owns this contract
        if(contract.getStudent() != user)
        {
            log.info("Not Authorized for this request");
            throw new CustomException("Not Authorized for this request",HttpStatus.BAD_REQUEST,"/");
        }

        Set<ContractLogs> contractLogs = new HashSet<>();
        if(isVerified == -1)
        {
            contractLogs = contract.getContractLogsSet();
        }
        else
        {
            for(ContractLogs current_log : contract.getContractLogsSet())
            {
                if(current_log.isVerified() == (isVerified > 0))
                {
                    contractLogs.add(current_log);
                }
            }
            //contractLogs = (Set<ContractLogs>) contract.getContractLogsSet().stream().filter(contractLogs1 -> contractLogs1.isVerified() == (isVerified > 0));
        }

        //based on isRequested get logs

        //contractLogs = contractLogRepository.findAllByIdAndUpdateRequested(id,isRequested > 0);


        log.info("Exiting requestGetAllLogs service");

        return contractLogs;
    }

    public Object getAllLogsFilter(long id, int isVerified, int isRequested) {
        log.info("In getAllLogsFilter service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get all logs of this contract if this belongs to user

        List<ContractLogs> contractLogs;

        //based on parameters get logs
        if(isVerified == -1 && isRequested == -1)
        {
            //Get All Logs without any filter
            contractLogs = contractLogRepository.findAllById(id);
        }
        else if(isRequested == -1)
        {
            contractLogs = contractLogRepository.findAllByIdAndIsVerified(id,isVerified > 0);
        }
        else if(isVerified == -1)
        {
            contractLogs = contractLogRepository.findAllByIdAndUpdateRequested(id,isRequested > 0);
        }
        else
        {
            contractLogs = contractLogRepository.findAllByIdAndUpdateRequestedAndIsVerified(id,isRequested > 0,isVerified > 0);
        }



        log.info("Exiting getAllLogsFilter service");

        return contractLogs;

    }

    public Object requestUpdateLog(ContractLogDTO contractLogDTO, long contract_id, long log_id) {
        log.info("In requestUpdateLog service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();

        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get this log of this contract if this belongs to this user(owner)
        Optional<RequestContract> contractOptional = requestContractRepository.findById(contract_id);
        if(contractOptional.isEmpty())
        {
            throw new CustomException("Contract Not Found",HttpStatus.NOT_FOUND,"/");
        }

        RequestContract contract = contractOptional.get();
        //check if this user owns this contract
        if(contract.getStudent() != user)
        {
            log.info("Not Authorized for this request");
            throw new CustomException("Not Authorized for this request",HttpStatus.BAD_REQUEST,"/");
        }

        ContractLogs contractLog = null;

        for(ContractLogs current_log : contract.getContractLogsSet())
        {
            if(current_log.getId() == log_id)
            {
                contractLog = current_log;
                break;
            }
        }



        //ContractLogs contractLog = contractLogRepository.findById(log_id).get();

        if(contractLogDTO.getLogMessage() != null)
            contractLog.setLogMessage(contractLogDTO.getLogMessage());

        if(contractLogDTO.getCreatedDate() != null)
            contractLog.setCreatedDate(contractLogDTO.getCreatedDate());

        if(contractLogDTO.getEndDate() != null)
            contractLog.setEndDate(contractLogDTO.getEndDate());

        //calculate new duration
        LocalDateTime tempDateTime = LocalDateTime.from( contractLog.getCreatedDate() );

        long minutes = tempDateTime.until( contractLog.getEndDate(), ChronoUnit.MINUTES );
        BigDecimal inMinutes = new BigDecimal(minutes);

        contractLog.setLectureDuration(inMinutes);

        //change updateRequested status
        contractLog.setUpdateRequested(false);

        //save this updated log
        contractLogRepository.save(contractLog);

        log.info("In requestUpdateLog service");
        return contractLog;

    }
}
