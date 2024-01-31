package com.mvp.hotel.booking.mvp_hotel_booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	@Query(value = "SELECT * FROM hotel  WHERE hotel.available_from >= ?1 AND hotel.available_to <= ?2 AND hotel.ID NOT IN " +
            "(SELECT hotel_id FROM booking WHERE (check_in >= ?1 OR check_out <= ?2))", nativeQuery = true)
    List<Hotel> findAllBetweenDates(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);
	
}
