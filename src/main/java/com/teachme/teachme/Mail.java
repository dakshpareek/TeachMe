package com.teachme.teachme;

import com.teachme.teachme.Entity.DAOUser;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class Mail {

    public SimpleMailMessage createmail(DAOUser user, String subject, String confirmationurl, String message ){

        String recipientaddress = user.getEmail();
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo( recipientaddress );
        email.setSubject( subject );
        email.setText( message + "\r\n" + "http://localhost:8080" + confirmationurl );
        return email;
    }
}
