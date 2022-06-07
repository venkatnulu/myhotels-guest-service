package com.myhotels.guestservice.dto;

import static javax.persistence.CascadeType.ALL;

import com.myhotels.guestservice.entities.CreditCard;
import com.myhotels.guestservice.entities.StayInfo;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestRequest {
    @NotBlank(message = "Guest name should not be empty")
    private String guestName;
    @NotBlank(message = "Guest password should not be empty")
    private String guestPassword;
    @NotBlank(message = "Guest email should not be empty")
    @Email
    private String guestEmail;
    private String guestAddress;
    @Min(1000000000L)
    @Max(9999999999L)
    private Long guestPhoneNumber;
    private Set<@Valid StayInfoRequest> stayHistory;
    private Set<@Valid CreditCardRequest> creditCards;
}
