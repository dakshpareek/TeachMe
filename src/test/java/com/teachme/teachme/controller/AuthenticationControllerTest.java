package com.teachme.teachme.controller;

import com.teachme.teachme.Controller.AuthenticationController;
import com.teachme.teachme.DTO.UserDTO;
import com.teachme.teachme.jwt.JwtTokenUtil;
import com.teachme.teachme.security.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

/*
    @InjectMocks
    AuthenticationController authenticationController;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    JwtUserDetailsService jwtUserDetailsService;
    //@Mock
    //UserDTO userDTO;

    @Test
    void saveUserWithNoPassword() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UserDTO userDTO = new UserDTO();
        userDTO.setName("username");
        userDTO.setEmail("user@email.com");
        userDTO.setPassword("");

        ResponseEntity<?> responseEntity = authenticationController.saveUser(userDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
    */

}