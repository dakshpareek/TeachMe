package com.teachme.teachme.repository;

import com.teachme.teachme.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Integer > {

    RegistrationToken findByToken( String token );
}
