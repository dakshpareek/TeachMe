package com.teachme.teachme.Entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Skill {

    @Id
    @GeneratedValue
    private int skill_id;

    @NotNull
    @Column( unique = true)
    private String name;

    private String description;

    private boolean verificationstatus;

    private boolean isdeleted;

    public Skill(){ }

    public Skill( String name ) {
        this.name = name;
        this.verificationstatus = true; // actually is supposed to be initialized by value false, but since the code for verification of skill is not written it is initialized with true
        this.description = "The description of " + name;
        this.isdeleted = false;
    }

    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVerificationstatus() {
        return verificationstatus;
    }

    public void setVerificationstatus(boolean verificationstatus) {
        this.verificationstatus = verificationstatus;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
}
