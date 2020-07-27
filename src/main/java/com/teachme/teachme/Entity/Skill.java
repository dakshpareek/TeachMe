package com.teachme.teachme.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column( unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private boolean verificationstatus;

    @Column
    private boolean isdeleted;

    @ManyToMany(mappedBy = "skills",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Experience> experienceSet = new HashSet<>();


    @ManyToMany(mappedBy = "skills",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<DAOUser> daoUserSet = new HashSet<>();


    public Skill(){ }

    public Skill( String name ) {
        this.name = name;
        this.verificationstatus = true; // actually is supposed to be initialized by value false, but since the code for verification of skill is not written it is initialized with true
        this.description = "The description of " + name;
        this.isdeleted = false;
    }

    public void addExperience(Experience experience) {
        experienceSet.add(experience);
        experience.getSkills().add(this);
    }

    public void addUser(DAOUser user)
    {
        daoUserSet.add(user);
        user.getSkills().add(this);
    }

}
