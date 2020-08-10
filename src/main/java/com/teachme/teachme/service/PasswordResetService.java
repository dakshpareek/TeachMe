package com.teachme.teachme.service;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.PasswordResetToken;
import com.teachme.teachme.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetService( PasswordResetTokenRepository passwordResetTokenRepository ) {

        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public void createpasswordresettoken(DAOUser user, String token ){

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save( passwordResetToken );
    }

    public PasswordResetToken getpasswordresettoken( String token ){

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken( token );

        if( passwordResetToken == null ){

            return null;
        }

        return passwordResetToken;
    }

    public void deletepasswordresettoken( PasswordResetToken passwordResetToken ){

        passwordResetTokenRepository.deleteById( passwordResetToken.getId() );
    }

}
