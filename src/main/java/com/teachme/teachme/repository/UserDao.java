package com.teachme.teachme.repository;


import com.teachme.teachme.entity.DAOUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<DAOUser, Integer> {
    //DAOUser findByUsername(String username);

    //@EntityGraph(attributePaths = "authorities")
    //Optional<DAOUser> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<DAOUser> findOneWithAuthoritiesByEmail(String email);

    //boolean findByEmail(String email);
    boolean existsByEmail(String email);
}
