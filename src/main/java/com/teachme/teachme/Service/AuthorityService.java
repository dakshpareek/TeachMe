package com.teachme.teachme.Service;

import com.teachme.teachme.Entity.Authority;
import com.teachme.teachme.DTO.AuthorityDTO;
import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.Repository.AuthorityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthorityService {

    private AuthorityRepository authorityRepository;
    private UserService userService;

    public AuthorityService(AuthorityRepository authorityRepository,UserService userService)
    {
        this.authorityRepository = authorityRepository;
        this.userService = userService;
    }

    public Authority createAuthorityService(AuthorityDTO authorityDTO)
    {
        if (authorityRepository.existsByName(authorityDTO.getName()) == true)
        {
            throw new CustomException(
                    "Duplicate Entry For Role",
                    HttpStatus.BAD_REQUEST, "/authority");
        }
        Authority authority = new Authority();

        authority.setName(authorityDTO.getName());
        //System.out.println("Here");
        //System.out.println(authority.toString());
        authorityRepository.save(authority);
        return authority;
    }

    public DAOUser setAuthorityService(AuthorityDTO authorityDTO) {
        Optional<Authority> authority = authorityRepository.findByName(authorityDTO.getName());
        if (authority.isEmpty())
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.BAD_REQUEST, "/assign_authority");
        }

        return userService.setAuthority(authority.get());
    }

    public void deleteAuthorityService(AuthorityDTO authorityDTO) {
        if (authorityRepository.existsByName(authorityDTO.getName()) == false)
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.NOT_FOUND, "/authority");
        }
        //first delete its associations from user_authority table

        //delete this authority from table
        authorityRepository.deleteByName(authorityDTO.getName());
    }

    public DAOUser removeAuthorityService(AuthorityDTO authorityDTO) {
        Optional<Authority> authority = authorityRepository.findByName(authorityDTO.getName());
        if (authority.isEmpty())
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.BAD_REQUEST, "/remove_authority");
        }
        return userService.removeAuthority(authority.get());
    }
}
