package com.teachme.teachme.security;


import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.DTO.UserDTO;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.Repository.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    //@Autowired
    private UserDao userDao;

    //@Autowired
    private PasswordEncoder bcryptEncoder;

    //@Autowired
    public JwtUserDetailsService(UserDao userDao,PasswordEncoder bcryptEncoder) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userDao.findOneWithAuthoritiesByEmail(email)
                .map(user -> createSpringSecurityUser(user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " was not found in the database"));

    }

    private User createSpringSecurityUser(DAOUser user) {

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }


    public DAOUser save(UserDTO user){
        if (userDao.existsByEmail(user.getEmail()) == true)
        {

            throw new CustomException(
                    "Duplicate Entry For Email ",
                    HttpStatus.BAD_REQUEST, "/register");
        }

        DAOUser newUser = new DAOUser();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userDao.save(newUser);
        return newUser;
        //return save;
    }

}