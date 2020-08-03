package com.teachme.teachme.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseUpdateDTO {

    @Size(min = 6, message = "Title must be greater 6")
    private String title;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal offeredPrice;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal courseDuration;

}
