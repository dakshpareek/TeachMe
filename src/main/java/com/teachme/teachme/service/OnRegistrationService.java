package com.teachme.teachme.service;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.RegistrationToken;
import com.teachme.teachme.repository.RegistrationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class OnRegistrationService {

    private RegistrationTokenRepository registrationTokenRepository;

    public OnRegistrationService( RegistrationTokenRepository registrationTokenRepository ) {

        this.registrationTokenRepository = registrationTokenRepository;
    }

    public void createregistrationtoken(DAOUser user, String token ){

        RegistrationToken registrationToken = new RegistrationToken( token, user );
        registrationTokenRepository.save( registrationToken );
    }

    public RegistrationToken getregistrationtoken( String token ){

        RegistrationToken registrationToken = registrationTokenRepository.findByToken( token );

        if( registrationToken == null ){

            return null;
        }

        return registrationToken;
    }

    public void deleteregistrationtoken( RegistrationToken registrationToken ){

        registrationTokenRepository.deleteById( registrationToken.getId() );
    }

}
