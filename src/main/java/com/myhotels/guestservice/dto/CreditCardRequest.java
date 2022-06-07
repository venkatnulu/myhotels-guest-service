package com.myhotels.guestservice.dto;

import com.myhotels.guestservice.entities.CardType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardRequest {

    @Min(100)
    @Max(999)
    private Integer cvv;
    private CardType cardType;
    @NotNull(message = "Expiry Month should not be empty")
    @Min(value = 1, message = "Expiry Month should be between 1 and 12. Should be future month")
    @Max(value = 12, message = "Expiry Month should be between 1 and 12. Should be future month")
    private Integer expiryMonth;
    @NotNull(message = "Expiry year should not be blank and should be future year")
    private Integer expiryYear;
}
