package com.teachme.teachme.event;

import com.teachme.teachme.Entity.DAOUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationEvent extends ApplicationEvent {

    private String appurl;

    private Locale locale;

    private DAOUser user;


    public OnRegistrationEvent( String appurl, Locale locale, DAOUser user) {

        super( user );
        this.appurl = appurl;
        this.locale = locale;
        this.user = user;
    }

}
