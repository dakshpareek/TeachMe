package com.teachme.teachme.controller;

import com.teachme.teachme.Service.PasswordResetService;
import com.teachme.teachme.dto.NewPasswordDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.entity.PasswordResetToken;
import com.teachme.teachme.repository.UserDao;
import com.teachme.teachme.Service.UserService;
import com.teachme.teachme.event.PasswordResetEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@RestController
public class PasswordResetController {

    private UserDao userrepository;

    private ApplicationEventPublisher applicationEventPublisher;

    private UserService userService;

    private MessageSource messageSource;

    private PasswordResetService passwordResetService;

    public PasswordResetController( UserDao userrepository, ApplicationEventPublisher applicationEventPublisher,
                                    UserService userService, MessageSource messageSource, PasswordResetService passwordResetService ){

        this.userrepository = userrepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userService = userService;
        this.messageSource = messageSource;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping( "/ResetPassword" )
    public ResponseEntity<?> resetpassword(@RequestParam( name = "email") String email, HttpServletRequest request ){

        Optional<DAOUser> userOptional = userrepository.findByEmail( email );

        if( userOptional.isEmpty() ){

            return new ResponseEntity<>( "User not found", HttpStatus.BAD_REQUEST );
        }

        try {

            DAOUser user = userOptional.get();
            String appurl = request.getContextPath();
            applicationEventPublisher.publishEvent( new PasswordResetEvent( appurl, request.getLocale(), user ));
        }
        catch ( RuntimeException ex ){

            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>("Check your email for verification", HttpStatus.OK );
    }

    @GetMapping( "/PasswordResetConfirmation" )
    public ResponseEntity<?> confirmuserforpasswordreset(WebRequest request, Model model, @RequestParam( "token" ) String token ){

        Locale locale = request.getLocale();
        PasswordResetToken passwordResetToken = passwordResetService.getpasswordresettoken( token );

        if( passwordResetToken == null ){

            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        Calendar calendar = Calendar.getInstance();

        if (( passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        return new ResponseEntity<>( "redirect:/UpdatePassword.html?lang=" + request.getLocale().getLanguage() + "?token=" + token , HttpStatus.OK );
    }

    @PostMapping( "/UpdatePassword" )
    public ResponseEntity<?> updatepassword(WebRequest request, Model model, @RequestParam( "token" ) String token,
                                            @RequestBody NewPasswordDTO newPasswordDTO ){

        Locale locale = request.getLocale();
        PasswordResetToken passwordResetToken = passwordResetService.getpasswordresettoken( token );

        if( passwordResetToken == null ){

            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        Calendar calendar = Calendar.getInstance();

        if (( passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return new ResponseEntity<>( "redirect:/badUser.html?lang=" + locale.getLanguage(), HttpStatus.BAD_REQUEST );
        }

        DAOUser user = passwordResetToken.getUser();
        passwordResetService.deletepasswordresettoken( passwordResetToken );
        userService.changepassword( user, newPasswordDTO );
        return new ResponseEntity<>( "Password changed successfully" , HttpStatus.OK );
    }

}
