package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    void deleteByCardNumber(Long cardId);
}
