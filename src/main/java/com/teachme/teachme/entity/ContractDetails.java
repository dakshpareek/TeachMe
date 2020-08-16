package com.teachme.teachme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class ContractDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    private Date creationDate;

    private Date completionDate;

    private boolean isCompleted;

    private boolean isHourlyPricing;

    private int totalTimeInMins;

    private int price;

    private boolean isAccepted;

}
