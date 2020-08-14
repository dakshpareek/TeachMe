package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name can not be empty")
    @Size(min = 6, max = 30, message = "Name must be between 6 and 30 characters")
    @Column
    //employer name
    private String name;

    @NotBlank(message = "Description can not be empty")
    @Column
    private String description;

    @Past(message = "Date Must Be From Past")
    @Column
    private Date startdate;

    @Past(message = "Date Must Be From Past")
    @Column
    private Date enddate;

    @Column
    private Boolean currently_working;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "EXPERIENCE_SKILL",
            joinColumns = {@JoinColumn(name = "EXPERIENCE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SKILL_ID", referencedColumnName = "ID")})
    private Set<Skill> skills = new HashSet<>();

}
