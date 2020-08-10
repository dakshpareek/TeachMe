package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //Relations

    @JsonIgnore
    @ManyToMany(mappedBy = "skills",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Experience> experienceSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "skills",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<DAOUser> daoUserSet = new HashSet<>();

    @ManyToMany( mappedBy = "skills", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    private Set<Request> requestSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "skills",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Course> courseSet = new HashSet<>();

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

    public void removeExperience(Experience experience)
    {
        experienceSet.remove(experience);
        experience.getSkills().remove(this);
    }

    public void addUser(DAOUser user)
    {
        daoUserSet.add(user);
        user.getSkills().add(this);
    }

    public void addCourse(Course course)
    {
        courseSet.add(course);
        try {
            Set<Skill> skills = course.getSkills();
            skills.add(this);
        }
        catch(Exception e)
        {
            System.out.println("EX: Here"+e);
        }
    }

    public void removeCourse(Course course)
    {
        courseSet.remove(course);
        course.getSkills().remove(this);
    }

}
