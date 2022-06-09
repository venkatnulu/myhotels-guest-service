package com.myhotels.guestservice.repository;

import com.myhotels.guestservice.entities.StayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StayInfoRepository extends JpaRepository<StayInfo, Integer> {

    @Query(value = "DELETE FROM stay_info s WHERE s.stay_id=:stayId", nativeQuery = true)
    @Modifying
    void deleteByStayId(@Param("stayId") Integer stayId);
    @Query(value = "DELETE FROM room_numbers s WHERE s.stay_id=:stayId", nativeQuery = true)
    @Modifying
    void deleteRoomNumbers(Integer stayId);
}
