package com.teachme.teachme.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class CreateCourseContractDTO {

    private boolean isHourlyPricing;

    @Range( min = 50, max = 100000, message = "price should be between 50 and 100000")
    private int price;

    private int student_id;
}
