package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import jdk.jfr.Unsigned;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank( message = "Title cannot be blank")
    @Size( min = 6, max = 100, message = "Title must be between 6 to 100 characters")
    private String title;

    @NotBlank( message = "Description cannot be blank")
    @Size( min = 6, max = 500, message = "Description must be between 6 to 500 characters")
    private String description;

    @CreationTimestamp
    private LocalDateTime creation_date;

    @UpdateTimestamp
    private LocalDateTime updation_date;

    private boolean isPublic;

    @Range( min = 50, max = 100000, message = "Offered Price should be between 50 to 100000")
    private int offered_price;

    private boolean isHourlyPrice;

    private boolean isClosed;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn( name = "user_id", nullable = false)
    @OnDelete( action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
    @JoinTable( name = "RequestSkill",
                joinColumns = { @JoinColumn( name = "RequestId", referencedColumnName = "ID") },
                inverseJoinColumns = { @JoinColumn( name = "SkillId", referencedColumnName = "ID") })
    private Set<Skill> skills = new HashSet<>();

    @OneToMany( fetch = FetchType.EAGER,
                cascade = CascadeType.ALL,
                mappedBy = "request")
    private Set<RequestResponse> requestResponseSet = new HashSet<>();

}
