package com.teachme.teachme.repository;

import com.teachme.teachme.entity.ContractDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface ContractBaseRepository<T extends ContractDetails> extends JpaRepository<T, Long> {
    Optional<T> findById(long contract_id);
}
