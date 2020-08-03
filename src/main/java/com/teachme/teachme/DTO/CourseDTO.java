package com.teachme.teachme.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseDTO {
    @NotBlank(message = "Title can not be empty")
    @Size(min = 6, message = "Title must be greater 6")
    private String title;

    @NotBlank(message = "Description can not be empty")
    private String description;

    @NotNull(message = "Offered Price can not be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal offeredPrice;

    @NotNull(message = "Duration can not be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal courseDuration;

    private List<Integer> skillIdList;
}
