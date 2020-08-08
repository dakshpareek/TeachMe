package com.teachme.teachme.repository;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import com.teachme.teachme.entity.RequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestResponseRepository extends JpaRepository<RequestResponse, Integer> {


    List<RequestResponse> findAllByUser( DAOUser user );

    Optional<RequestResponse> findByRequestAndUser( Request request, DAOUser user );

}
