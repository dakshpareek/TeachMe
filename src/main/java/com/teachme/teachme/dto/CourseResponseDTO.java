package com.teachme.teachme.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teachme.teachme.entity.Course;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CourseResponseDTO {
    @NotBlank(message = "Message can not be empty")
    private String message;

    @NotNull(message = "Proposed Price can not be null")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal proposedPrice;

}
