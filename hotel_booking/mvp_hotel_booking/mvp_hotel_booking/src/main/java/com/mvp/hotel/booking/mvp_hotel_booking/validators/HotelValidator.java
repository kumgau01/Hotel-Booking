package com.mvp.hotel.booking.mvp_hotel_booking.validators;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.ErrorMessage;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.ValidTypesOfHotelsEnum;
import com.mvp.hotel.booking.mvp_hotel_booking.exceptions.InvalidRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HotelValidator extends BaseValidator {
	public static void validateHotelPOST(Hotel hotel) {
        validateName(hotel.getName());
        validateType(hotel.getType());
        validateDates(hotel.getAvailableFrom(), hotel.getAvailableTo());
    }

    public static void validateHotelPATCH(Hotel hotel) {
        validateId(hotel.getId());
        validateName(hotel.getName());
        validateType(hotel.getType());
        validateDates(hotel.getAvailableFrom(), hotel.getAvailableTo());
    }

    public static void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            log.error("Hotel name cannot be null...");
            throw new InvalidRequestException(ErrorMessage.INVALID_NAME);
        }
    }

    public static void validateType(ValidTypesOfHotelsEnum type) {
        if (type == null || !Arrays.asList("DELUXE", "LUXURY", "SUITE").contains(type.toString())) {
            log.error("The type parameter: '{}' is invalid, must be one of the following [DELUXE, LUXURY, SUITE]", type);
            throw new InvalidRequestException(ErrorMessage.INVALID_TYPE);
        }
    }
}
