package com.myhotels.guestservice.entities;

import static javax.persistence.CascadeType.ALL;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestId;
    private String guestName;
    private String guestPassword;
    private String guestEmail;
    private String guestAddress;
    private Long guestPhoneNumber;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = ALL
    )
    @JoinColumn(
            name = "guest_id",
            referencedColumnName = "guestId"
    )
    private Set<StayInfo> stayHistory;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = ALL
    )
    @JoinColumn(
            name = "guest_id",
            referencedColumnName = "guestId"
    )
    private Set<CreditCard> creditCards;
}
