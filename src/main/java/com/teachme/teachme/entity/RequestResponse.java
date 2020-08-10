package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class RequestResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn( name = "request_id", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Request request;

    @OneToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "user_id", nullable = false)
    private DAOUser user;

    @Range( min = 50, max = 100000, message = "Offered Price should be between 50 to 100000")
    private int proposed_price;

    private boolean isHourlyPrice;

    @NotBlank( message = "Message cannot be blank")
    @Size( min = 10, max = 500, message = "Message must be between 10 to 500 characters")
    private String message;

    private boolean isAccepted;

}
