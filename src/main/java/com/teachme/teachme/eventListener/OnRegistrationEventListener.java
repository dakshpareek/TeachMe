package com.teachme.teachme.eventListener;

import com.teachme.teachme.Entity.DAOUser;
import com.teachme.teachme.Mail;
import com.teachme.teachme.Service.UserService;
import com.teachme.teachme.event.OnRegistrationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OnRegistrationEventListener implements ApplicationListener<OnRegistrationEvent> {


    private UserService userService;

    private MessageSource messageSource;

    //private JavaMailSender mailSender;

    private Mail mail;


    public OnRegistrationEventListener( UserService userService, MessageSource messageSource, Mail mail ){

        this.userService = userService;
        this.messageSource = messageSource;
        this.mail = mail;
    }

    @Override
    public void onApplicationEvent(OnRegistrationEvent onRegistrationEvent) {

        listenonregistrationevent( onRegistrationEvent );
    }

    public void listenonregistrationevent( OnRegistrationEvent onRegistrationEvent ){

        DAOUser user = onRegistrationEvent.getUser();
        String token = UUID.randomUUID().toString();
        userService.createregistrationtoken( user, token );

        String confirmationurl = onRegistrationEvent.getAppurl() + "/registrationconfirmation.html?token=" + token;
        //String message = messageSource.getMessage("message.regSucc", null, onRegistrationEvent.getLocale());
        String message = "Message Heree";
        SimpleMailMessage email = mail.createmail( user, "Registration Confirmation", confirmationurl, message );

        System.out.println( "registration token : " + token );
        //mailSender.send( email );
    }
}
