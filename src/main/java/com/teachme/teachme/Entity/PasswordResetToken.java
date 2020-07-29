package com.teachme.teachme.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private int id;

    private String token;

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "user_id" )
    private DAOUser user;

    private Date expiryDate;

    public PasswordResetToken(){

    }

    public PasswordResetToken( String token, DAOUser user ){

        this.token = token;
        this.user = user;
        this.expiryDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( expiryDate );
        calendar.add( calendar.HOUR, 24 );
        this.expiryDate = calendar.getTime();
    }

}
