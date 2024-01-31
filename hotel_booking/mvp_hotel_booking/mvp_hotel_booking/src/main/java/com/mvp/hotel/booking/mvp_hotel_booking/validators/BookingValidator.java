package com.mvp.hotel.booking.mvp_hotel_booking.validators;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.ErrorMessage;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;
import com.mvp.hotel.booking.mvp_hotel_booking.exceptions.InvalidRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingValidator extends BaseValidator {
	public static void validateBookingPOST(Booking booking) {
        validateDates(booking.getCheckIn(), booking.getCheckOut());
        validateGuest(booking.getGuests());
        //validateStatus(booking.getStatus());
    }

	/*
	 * private static void validateStatus(String status) { // TODO Auto-generated
	 * method stub if (status.isEmpty()) { log.
	 * error("Invalid status value: '{}', possible values are available and not-available."
	 * , status); throw new InvalidRequestException(ErrorMessage.INVALID_STATUS); }
	 * }
	 */

	public static void validateGuest(Integer guests) {
        if (guests == null || guests <= 0) {
            log.error("Invalid guests number: '{}', guests must be a non-zero number.", guests);
            throw new InvalidRequestException(ErrorMessage.INVALID_GUESTS);
        }
    }
}
