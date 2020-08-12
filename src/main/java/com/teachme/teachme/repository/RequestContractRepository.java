package com.teachme.teachme.repository;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestContractRepository extends JpaRepository<RequestContract, Long> {

    List<RequestContract> findAllByStudent( DAOUser student );

    List<RequestContract> findAllByTeacher( DAOUser teacher );

    Optional<RequestContract> findByRequest(Request request);

    Optional<RequestContract> findById(long id);

    //Optional<RequestContract> findByIdAndStudent(long contract_id,DAOUser student);

}
