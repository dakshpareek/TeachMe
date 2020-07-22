package com.teachme.teachme.service;

import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
