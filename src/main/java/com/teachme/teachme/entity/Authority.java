package com.teachme.teachme.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(min=6,max=15,message = "Authority Name Must Be Between of Length 6 to 15")
    @NotBlank(message = "Name Can Not Be Empty")
    private String name;


}
