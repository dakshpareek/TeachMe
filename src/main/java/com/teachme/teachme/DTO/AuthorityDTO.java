package com.teachme.teachme.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class AuthorityDTO {

    //@Pattern(regexp = "^ROLE_")
    @Column
    @NotBlank(message = "Name Can Not Be Empty")
    @Size(min=6,max=15,message = "Authority Name Must Be Between of Length 6 to 15")
    private String name;
}
