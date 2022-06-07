package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.Guest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByGuestId(Integer guestId);
}
