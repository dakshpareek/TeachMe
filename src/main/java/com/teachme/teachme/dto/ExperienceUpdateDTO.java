package com.teachme.teachme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter

public class ExperienceUpdateDTO {

    @NotBlank(message = "Name can not be empty")
    @Size(min = 6, max = 30, message = "Name must be between 6 and 30 characters")
    private String name;

    private String description;

    @Past(message = "Date Must Be From Past")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy", timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date startdate;

    @Past(message = "Date Must Be From Past")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy", timezone = JsonFormat.DEFAULT_TIMEZONE)
    private Date enddate;

    private Boolean currently_working;

}
