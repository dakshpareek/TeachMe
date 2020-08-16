package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class RequestContract {

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

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "student_id", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser student;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "teacher_id", nullable = false )
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser teacher;

    @OneToOne( targetEntity = Request.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "request_id" )
    private Request request;

    @OneToMany(targetEntity = ContractLogs.class, fetch = FetchType.LAZY)
    private Set<ContractLogs> contractLogsSet = new HashSet<>();

    private Set<ContractLogs> getContracts()
    {
        return contractLogsSet;
    }

    public void addContract(ContractLogs contractLog)
    {
        contractLogsSet.add(contractLog);
        contractLog.setRequestContract(this);
    }

}
