package com.teachme.teachme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Calendar;


@Entity
@Getter
@Setter
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "user_id" )
    private DAOUser user;

    private Date expiryDate;

    public RegistrationToken(){

    }

    public RegistrationToken( String token, DAOUser user ){

        this.token = token;
        this.user = user;
        this.expiryDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( expiryDate );
        calendar.add( calendar.HOUR, 24 );
        this.expiryDate = calendar.getTime();
    }

}
