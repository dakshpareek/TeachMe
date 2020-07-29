package com.teachme.teachme.Repository;

import com.teachme.teachme.Entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Integer > {

    RegistrationToken findByToken( String token );
}
