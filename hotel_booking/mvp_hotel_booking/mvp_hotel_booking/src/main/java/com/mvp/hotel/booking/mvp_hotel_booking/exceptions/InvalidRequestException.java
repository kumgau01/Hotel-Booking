package com.mvp.hotel.booking.mvp_hotel_booking.exceptions;

import java.io.Serial;

public class InvalidRequestException extends RuntimeException {
	@Serial
    private static final long serialVersionUID = 2178386706175989636L;

    public InvalidRequestException(String message) {
        super(message);
    }
}
