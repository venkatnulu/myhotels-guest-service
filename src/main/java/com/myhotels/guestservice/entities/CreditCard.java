package com.myhotels.guestservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_number", nullable = false)
    private Long cardNumber;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private Integer cvv;
    private Integer expiryMonth;
    private Integer expiryYear;

    @ManyToOne
    @JoinColumn(name = "guest_guest_id")
    private Guest guest;

}