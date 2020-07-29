package com.teachme.teachme.Controller;

import com.teachme.teachme.DTO.UserDTO;
import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.Entity.RegistrationToken;
import com.teachme.teachme.Service.UserService;
import com.teachme.teachme.event.OnRegistrationEvent;
import com.teachme.teachme.jwt.JwtTokenUtil;
import com.teachme.teachme.security.JwtRequest;
import com.teachme.teachme.security.JwtResponse;
import com.teachme.teachme.security.JwtUserDetailsService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

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

    private ApplicationEventPublisher applicationEventPublisher;

    private UserService userService;

    private MessageSource messageSource;

    private PasswordEncoder bcryptEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil,
                                    JwtUserDetailsService userDetailsService, ApplicationEventPublisher applicationEventPublisher,
                                    UserService userService, MessageSource messageSource, PasswordEncoder passwordEncoder ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userService = userService;
        this.messageSource = messageSource;
        this.bcryptEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        Authentication authentication = authenticate(authenticationRequest.getEmail(), bcryptEncoder.encode( authenticationRequest.getPassword() ) );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenUtil.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(new JwtResponse(token), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO user, HttpServletRequest request ){

        try {
            DAOUser registereduser = userDetailsService.save(user);
            String appurl = request.getContextPath();
            applicationEventPublisher.publishEvent( new OnRegistrationEvent( appurl, request.getLocale(), registereduser ));
        }
        catch ( RuntimeException ex ){

            System.out.println( ex );
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>("Check your email for verification", HttpStatus.OK );
    }

    @GetMapping( "/registrationconfirmation" )
    public ResponseEntity<?> confirmuserforregistration( WebRequest request, Model model, @RequestParam( "token" ) String token ){

        Locale locale = request.getLocale();
        RegistrationToken registrationToken = userService.getregistrationtoken( token );

        if( registrationToken == null ){

            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        Calendar calendar = Calendar.getInstance();

        if (( registrationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        DAOUser user = registrationToken.getUser();
        userService.enableuser( user );
        return new ResponseEntity<>( "redirect:/authenticate.html?lang=" + request.getLocale().getLanguage() , HttpStatus.OK );
    }

    private Authentication authenticate(String email, String password) throws Exception {
        try {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
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