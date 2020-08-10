package com.teachme.teachme.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestResponseDTO {

    @Range( min = 50, max = 100000, message = "Offered Price should be between 50 to 100000")
    private int proposed_price;

    private boolean isHourlyPrice;

    @NotBlank( message = "Message cannot be blank")
    @Size( min = 10, max = 500, message = "Message must be between 10 to 500 characters")
    private String message;

}
