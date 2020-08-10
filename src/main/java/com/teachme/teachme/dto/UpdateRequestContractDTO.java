package com.teachme.teachme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestContractDTO {

    private boolean isHourlyPricing;

    private int price;

}
