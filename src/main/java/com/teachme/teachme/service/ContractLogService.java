package com.teachme.teachme.service;

import com.teachme.teachme.dto.ContractLogDTO;
import com.teachme.teachme.entity.ContractLogs;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.repository.ContractLogRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ContractLogService {

    private ContractLogRepository contractLogRepository;
    private UserDao userRepository;
    private ContractLogs contractLogs;
    public ContractLogService(ContractLogRepository contractLogRepository,
                              UserDao userRepository) {
        this.contractLogRepository = contractLogRepository;
        this.userRepository = userRepository;
    }

    public Object createLogs(ContractLogDTO contractLogDTO, long id) {
        log.info("In createLogs service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //check this contract exists and it belongs to this user (check for both end)


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

        contractLogRepository.save(contractLog);

        log.info("Exiting createLogs service");
        return contractLog;

    }

    public Map<String, Object> changeStatus(long id, long log_id) {
        log.info("In changeStatus service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //Here we will check whether this user owns this log or not


        //after verifying log we will change status of that log
        ContractLogs contractLog = contractLogRepository.findById(log_id).get();

        contractLog.setVerified(true);

        contractLogRepository.save(contractLog);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Successfully Changed Status Of Log");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting changeStatus service");
        return body;
    }

    public Object updateRequested(long id, long log_id) {
        log.info("In updateRequested service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //Here we will check whether this user belongs to this contract or not (this user is not owner of contract)

        ContractLogs contractLog = contractLogRepository.findById(log_id).get();

        //request for update
        contractLog.setUpdateRequested(true);

        //save this contract now
        contractLogRepository.save(contractLog);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Successfully Requested for modification");
        body.put("status",200);
        body.put("path","/");

        log.info("Exiting updateRequested service");
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

    public Object getAllRequestedLogs(long id, int isRequested) {
        log.info("In getAllRequestedLogs service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get all logs of this contract if this belongs to user

        List<ContractLogs> contractLogs;

        //based on isRequested get logs

        contractLogs = contractLogRepository.findAllByIdAndUpdateRequested(id,isRequested > 0);



        log.info("Exiting getAllRequestedLogs service");

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

    public Object updateLog(ContractLogDTO contractLogDTO, long id, long log_id) {
        log.info("In updateLog service");

        //get user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        DAOUser user = userRepository.findByEmail(currentUsername).get();

        //get this log of this contract if this belongs to this user(owner)

        ContractLogs contractLog = contractLogRepository.findById(log_id).get();

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

        log.info("In updateLog service");
        return contractLog;

    }
}
