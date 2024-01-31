package com.mvp.hotel.booking.mvp_hotel_booking.service;

import java.util.List;

import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;

public interface BookingService {
	List<Booking> getAllBookings();
    Booking getBookings(Integer id);
    IdEntity saveBookings(Booking booking);
    SuccessEntity deleteBooking(Integer id);
    boolean validateHotelExistenceById(Integer id);
    boolean dateIsBefore(String date1, String date2);
    boolean bookingOverlaps(Booking Bookings);
    boolean validateBookingExistence(Integer id);
}
