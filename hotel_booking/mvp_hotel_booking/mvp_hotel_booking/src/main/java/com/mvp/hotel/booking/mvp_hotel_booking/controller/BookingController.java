package com.mvp.hotel.booking.mvp_hotel_booking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;
import com.mvp.hotel.booking.mvp_hotel_booking.service.BookingService;
import com.mvp.hotel.booking.mvp_hotel_booking.validators.BookingValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookingController {
	private final BookingService bookingService;

    @GetMapping(value = "/bookings", produces = "application/json")
    public List<Booking> getBookingList(){
        log.info("Get all bookings...");
        return bookingService.getAllBookings();
    }
    
    @GetMapping(value = "/booking/{id}", produces = "application/json")
    public Booking getBooking(@PathVariable Integer id){
        BookingValidator.validateId(id);
        log.info("Get a user specified booking with id = {}", id);
        return bookingService.getBookings(id);
    }

    @PostMapping(value = "/booking", produces = "application/json")
    public IdEntity saveBooking(@RequestBody Booking booking){
        BookingValidator.validateBookingPOST(booking);
        log.info("Save a user specified booking...");
        return bookingService.saveBookings(booking);
    }

    @DeleteMapping(value = "/booking/{id}", produces = "application/json")
    public SuccessEntity deleteBooking(@PathVariable Integer id){
        BookingValidator.validateId(id);
        log.info("Delete a user specified booking...");
        return bookingService.deleteBooking(id);
    }
}
