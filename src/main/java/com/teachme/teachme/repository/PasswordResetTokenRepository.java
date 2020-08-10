package com.teachme.teachme.repository;

import com.teachme.teachme.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer > {

    PasswordResetToken findByToken( String token );
}
