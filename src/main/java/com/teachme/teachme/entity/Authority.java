package com.teachme.teachme.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @Size(min=6,max=15,message = "Authority Name Must Be Between of Length 6 to 15")
    @NotBlank(message = "Name Can Not Be Empty")
    private String name;


}
