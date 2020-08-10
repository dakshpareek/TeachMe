package com.teachme.teachme.eventListener;

import com.teachme.teachme.service.PasswordResetService;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.Mail;
import com.teachme.teachme.service.UserService;
import com.teachme.teachme.event.PasswordResetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordResetEventListener implements ApplicationListener<PasswordResetEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    /*
    @Autowired
    private JavaMailSender mailSender;
    */

    @Autowired
    private Mail mail;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetEventListener(){}


    /*
    public PasswordResetEventListener(UserService userService, MessageSource messageSource, Mail mail, PasswordResetService passwordResetService ){

        this.userService = userService;
        this.messageSource = messageSource;
        this.mail = mail;
        this.passwordResetService = passwordResetService;
    }*/

    @Override
    public void onApplicationEvent(PasswordResetEvent passwordResetEvent) {

        listenpasswprdresetevent( passwordResetEvent );
    }

    public void listenpasswprdresetevent( PasswordResetEvent passwordResetEvent ){

        DAOUser user = passwordResetEvent.getUser();
        String token = UUID.randomUUID().toString();
        passwordResetService.createpasswordresettoken( user, token );
        String confirmationurl = passwordResetEvent.getAppurl() + "/PasswordResetConfirmation.html?token=" + token;
        //String message = messageSource.getMessage("message.regSucc", null, passwordResetEvent.getLocale());
        String message = "Message Heree";
        SimpleMailMessage email = mail.createmail( user, "Password reset confirmation", confirmationurl, message );

        System.out.println( "password reset token : " + token );

        //mailSender.send( email );
    }

}
