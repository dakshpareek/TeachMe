package com.teachme.teachme.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {

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
    //@JsonIgnore
    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, message = "Password must be between 6 and 15 characters")
    private String password;
}
