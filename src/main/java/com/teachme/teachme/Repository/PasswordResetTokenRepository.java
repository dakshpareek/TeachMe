package com.teachme.teachme.Repository;

import com.teachme.teachme.Entity.PasswordResetToken;
import com.teachme.teachme.Entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer > {

    PasswordResetToken findByToken( String token );
}
