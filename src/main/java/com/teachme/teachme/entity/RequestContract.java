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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private Date creationDate;

    private Date completionDate;

    private boolean isCompleted;

    private boolean isHourlyPricing;

    private int totalTimeInMins;

    private int price;

    private boolean isAccepted;
    
    
    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "student_id")
    private DAOUser student;

    @OneToOne( targetEntity = DAOUser.class, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "teacher_id" )
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
