package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class DAOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name can not be empty")
    @Size(min = 6, max = 30, message = "Name must be between 6 and 30 characters")
    @Column
    private String name;

    @Column(unique=true)
    @NotBlank(message = "Email Can not be empty")
    private String email;

    @Column
    @Size(min = 10, max =10, message = "Phone Number must have 10 digits")
    private String phone;

    @Column
    @JsonIgnore
    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, message = "Password must be between 6 and 15 characters")
    private String password;


    @CreationTimestamp
    private LocalDateTime created_date;

    @UpdateTimestamp
    private LocalDateTime updated_date;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
    private Set<Authority> authorities = new HashSet<>();



    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_SKILL",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SKILL_ID", referencedColumnName = "ID")})
    private Set<Skill> skills = new HashSet<>();

    private boolean enabled;

    @OneToOne( fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                mappedBy = "user")
    private RegistrationToken registrationToken;

    @OneToOne( fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    @OneToOne( fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                mappedBy = "user" )
    private RequestResponse requestResponse;

    @OneToOne( fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                mappedBy = "student" )
    private RequestContract requestContract;

    @OneToOne( fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "teacher" )
    private RequestContract requestContract1;

}
