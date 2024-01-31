package com.mvp.hotel.booking.mvp_hotel_booking.validators;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.AppConstants;
import com.mvp.hotel.booking.mvp_hotel_booking.constants.ErrorMessage;
import com.mvp.hotel.booking.mvp_hotel_booking.exceptions.InvalidRequestException;

public class PageNumberAndSizeValidator {
	public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new InvalidRequestException(ErrorMessage.PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO);
        }

        if (size < 0) {
            throw new InvalidRequestException(ErrorMessage.SIZE_NUMBER_CANNOT_BE_LESS_THAN_ZERO);
        }

        if (size > Integer.parseInt(AppConstants.MAX_PAGE_SIZE)) {
            throw new InvalidRequestException(ErrorMessage.MAX_PAGE_SIZE_EXCEPTION);
        }
    }
}
