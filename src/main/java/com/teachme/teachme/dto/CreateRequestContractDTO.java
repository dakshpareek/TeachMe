package com.teachme.teachme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequestContractDTO {

    private boolean isHourlyPricing;

    private int price;

    private int teacher_id;

}
