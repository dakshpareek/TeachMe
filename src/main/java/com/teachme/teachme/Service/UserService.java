package com.teachme.teachme.Service;

import com.teachme.teachme.dto.NewPasswordDTO;
import com.teachme.teachme.entity.Authority;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.PasswordResetToken;
import com.teachme.teachme.entity.RegistrationToken;
import com.teachme.teachme.repository.PasswordResetTokenRepository;
import com.teachme.teachme.repository.RegistrationTokenRepository;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    //@Autowired
    private UserDao userRepository;

    private RegistrationTokenRepository registrationTokenRepository;

    private PasswordResetTokenRepository passwordResetTokenRepository;

    private PasswordEncoder bcryptEncoder;

    public UserService(UserDao userRepository, RegistrationTokenRepository registrationTokenRepository,
                       PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.registrationTokenRepository = registrationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.bcryptEncoder = passwordEncoder;

    }


    @Transactional(readOnly = true)
    public Optional<DAOUser> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }

    public DAOUser setAuthority(Authority authority)
    {
        //check jwt token and get name/email from it
        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
        DAOUser user = userRepository.findOneWithAuthoritiesByEmail(currentUsername.get()).get();

        //Check if authority is already assigned or not
        Set<Authority> authoritiesList = user.getAuthorities();
        if (authoritiesList.contains(authority) == false)
        {
            authoritiesList.add(authority);
            user.setAuthorities(authoritiesList);
            userRepository.save(user);
        }
        return user;
    }

    public DAOUser removeAuthority(Authority authority) {
        //check jwt token and get name/email from it
        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
        DAOUser user = userRepository.findOneWithAuthoritiesByEmail(currentUsername.get()).get();
        Set<Authority> authoritiesList = user.getAuthorities();

        if (authoritiesList.contains(authority) == true) {
            authoritiesList.remove(authority);
            userRepository.save(user);
        }
        return  user;
    }

    public void removeUser() {
        //delete current user
        String currentUsername = SecurityUtils.getCurrentUsername().get();
        userRepository.deleteByEmail(currentUsername);

        userRepository.deleteByEmail(currentUsername);

        //reset context of application
        SecurityContextHolder.clearContext();
    }

    public void createregistrationtoken( DAOUser user, String token ){

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


    public void enableuser( DAOUser user ){

        user.setEnabled( true );
        userRepository.save( user );
    }

    public void createpasswordresettoken( DAOUser user, String token ){

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

    public void changepassword(DAOUser user, NewPasswordDTO newPasswordDTO ){

        user.setPassword( bcryptEncoder.encode( newPasswordDTO.getNewpassword() ) );
        userRepository.save( user );
    }

}
