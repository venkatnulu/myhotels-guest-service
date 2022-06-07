package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.StayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayInfoRepository extends JpaRepository<StayInfo, Integer> {
    void deleteByStayId(Integer stayId);
}
