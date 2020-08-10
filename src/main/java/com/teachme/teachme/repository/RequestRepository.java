package com.teachme.teachme.repository;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByUser(DAOUser user);
}
