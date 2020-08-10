package com.teachme.teachme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class RequestContract extends ContractDetails {

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "student_id")
    private DAOUser student;

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "teacher_id" )
    private DAOUser teacher;

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "request_id" )
    private Request request;

}
