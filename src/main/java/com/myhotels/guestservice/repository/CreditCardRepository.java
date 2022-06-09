package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    @Query(value = "DELETE FROM credit_card c WHERE c.card_id=:cardId", nativeQuery = true)
    @Modifying
    void deleteByCardNumber(@Param("cardId") Long cardId);
}
