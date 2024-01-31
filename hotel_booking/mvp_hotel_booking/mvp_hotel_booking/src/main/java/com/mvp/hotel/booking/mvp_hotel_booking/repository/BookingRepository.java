package com.mvp.hotel.booking.mvp_hotel_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
}
