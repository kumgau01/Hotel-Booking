package com.mvp.hotel.booking.mvp_hotel_booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class HotelApiExceptionHandler {
	@ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ApiErrorMessage> handleInvalidRequest(InvalidRequestException e) {
        return handleBadRequest(e);
    }

    private ResponseEntity<ApiErrorMessage> handleBadRequest(Exception e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e); // get full stacktrace
        return new ResponseEntity<>(new ApiErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
