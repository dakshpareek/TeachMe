package com.teachme.teachme.controller;

import com.teachme.teachme.entity.UserDTO;
import com.teachme.teachme.jwt.JwtTokenUtil;
import com.teachme.teachme.security.JwtRequest;
import com.teachme.teachme.security.JwtResponse;
import com.teachme.teachme.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Validated
public class AuthenticationController {


    //@Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    private JwtTokenUtil jwtTokenUtil;

    //@Autowired
    private JwtUserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil,
                                    JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        System.out.println("Here");
        Authentication authentication = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenUtil.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(new JwtResponse(token), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO user){
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authenticate;
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        catch (Exception e)
        {
            //System.out.println(e);
            throw new Exception("EXCEPTION", e);
        }

    }
}