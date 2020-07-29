package com.teachme.teachme.eventListener;

import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.Mail;
import com.teachme.teachme.Service.UserService;
import com.teachme.teachme.event.OnRegistrationEvent;
import com.teachme.teachme.event.PasswordResetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    public PasswordResetEventListener(){}

    /*
    public PasswordResetEventListener(UserService userService, MessageSource messageSource, JavaMailSender javaMailSender, Mail mail ){

        this.userService = userService;
        this.messageSource = messageSource;
        this.mailSender = javaMailSender;
        this.mail = mail;
    }*/

    @Override
    public void onApplicationEvent(PasswordResetEvent passwordResetEvent) {

        listenpasswprdresetevent( passwordResetEvent );
    }

    public void listenpasswprdresetevent( PasswordResetEvent passwordResetEvent ){

        DAOUser user = passwordResetEvent.getUser();
        String token = UUID.randomUUID().toString();
        userService.createpasswordresettoken( user, token );

        String confirmationurl = passwordResetEvent.getAppurl() + "/PasswordResetConfirmation.html?token=" + token;
        //String message = messageSource.getMessage("message.regSucc", null, passwordResetEvent.getLocale());
        String message = "Message Heree";
        SimpleMailMessage email = mail.createmail( user, "Password reset confirmation", confirmationurl, message );

        System.out.println( "password reset token : " + token );

        //mailSender.send( email );
    }
}
