package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Guest, Integer> {

}
