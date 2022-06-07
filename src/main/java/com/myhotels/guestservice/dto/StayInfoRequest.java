package com.myhotels.guestservice.dto;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StayInfoRequest {

    @NotNull(message = "Hotel id should not be null")
    private Integer hotelId;
    private Set<Integer> roomNumbers;

}
