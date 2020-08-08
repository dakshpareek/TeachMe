package com.teachme.teachme.dto;

import com.teachme.teachme.entity.Skill;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class RequestDTO {

    @NotBlank( message = "Title cannot be blank")
    @Size( min = 6, max = 100, message = "Title must be between 6 to 100 characters")
    private String title;

    @NotBlank( message = "Description cannot be blank")
    @Size( min = 6, max = 500, message = "Description must be between 6 to 500 characters")
    private String description;

    private boolean isPublic;

    @Range( min = 50, max = 100000, message = "Offered Price should be between 50 to 100000")
    private int offered_price;

    private boolean isHourlyPrice;

    private boolean isClosed;

    private Set<Skill> skillSet;

}
