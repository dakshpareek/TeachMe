package com.teachme.teachme.repository;

import com.teachme.teachme.entity.Authority;
import com.teachme.teachme.entity.ContractLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ContractLogRepository extends JpaRepository<ContractLogs, Long> {
    //change these id to contract id after adding that in entity
    List<ContractLogs> findAllById(long id);
    List<ContractLogs> findAllByIdAndIsVerified(long id,boolean isVerified);


    List<ContractLogs> findAllByIdAndUpdateRequested(long id,boolean isRequested);

    List<ContractLogs> findAllByIdAndUpdateRequestedAndIsVerified(long id,boolean isRequested,boolean isVerified);

    Optional<ContractLogs> findByIdAndRequestContract(long id,long contract_id);

}
