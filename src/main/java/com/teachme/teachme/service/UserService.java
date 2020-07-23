package com.teachme.teachme.service;

import com.teachme.teachme.entity.Authority;
import com.teachme.teachme.entity.AuthorityDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    //@Autowired
    private UserDao userRepository;

    public UserService(UserDao userRepository) {
        this.userRepository = userRepository;
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

        //reset context of application
        SecurityContextHolder.clearContext();
    }
}
