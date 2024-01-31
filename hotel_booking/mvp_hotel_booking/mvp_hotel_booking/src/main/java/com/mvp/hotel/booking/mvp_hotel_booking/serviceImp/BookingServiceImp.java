package com.mvp.hotel.booking.mvp_hotel_booking.serviceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.ErrorMessage;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;
import com.mvp.hotel.booking.mvp_hotel_booking.exceptions.InvalidRequestException;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.BookingRepository;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.HotelRepository;
import com.mvp.hotel.booking.mvp_hotel_booking.service.BookingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BookingServiceImp implements BookingService {
	private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return bookingRepository.findAll();
	}

	@Override
	public Booking getBookings(Integer id) {
		// TODO Auto-generated method stub
		validateBookingExistence(id);
		return bookingRepository.findById(id).get();
	}

	@Override
	public IdEntity saveBookings(Booking booking) {
		// TODO Auto-generated method stub
		Integer bookingInventoryId = booking.getHotelId();

        //boolean to determine if the booking is valid through the existence of the inventory ID.
        //if the inventory ID exists, then continue
        if (validateHotelExistenceById(bookingInventoryId)) {
            //boolean to determine if there is already a pre-existing booking that overlaps with the users
            if (bookingOverlaps(booking)) {
                throw new InvalidRequestException(ErrorMessage.INVALID_DATE_OVERLAP);
            }
            //determine if the dates are out of the Inventory's bounds  //TODO: getById is deprecated
            if (dateIsBefore(hotelRepository.getById(booking.getHotelId()).getAvailableFrom(), booking.getCheckIn()) && dateIsBefore(booking.getCheckOut(), hotelRepository.getById(booking.getHotelId()).getAvailableTo())) {
                booking = bookingRepository.save(booking);
                IdEntity idEntity = new IdEntity();
                idEntity.setId(booking.getId());
                return idEntity;
            } else {
                throw new InvalidRequestException(ErrorMessage.INVALID_BOOKING_DATES);
            }
        } else {
            //Throw error if the Inventory ID does not exist
            throw new InvalidRequestException(ErrorMessage.INVALID_HOTEL_IN_BOOKING);
        }
	}

	@Override
	public SuccessEntity deleteBooking(Integer id) {
		// TODO Auto-generated method stub
		validateBookingExistence(id);
		bookingRepository.deleteById(id);
		SuccessEntity successEntity = new SuccessEntity();
        successEntity.setSuccess(!bookingRepository.existsById(id));
        return successEntity;
	}

	@Override
	public boolean validateHotelExistenceById(Integer id) {
		// TODO Auto-generated method stub
		if (!hotelRepository.existsById(id)) {
            throw new InvalidRequestException(ErrorMessage.INVALID_ID_EXISTENCE);
        } else if (hotelRepository.getById(id).getAvailableFrom() == null && hotelRepository.getById(id).getAvailableTo() == null) {  //TODO getById is deprecated
            //Checks if the inventory has available to and available from dates, if not then throw an error as a booking cannot be made.
            throw new InvalidRequestException(ErrorMessage.EMPTY_HOTEL_DATES);
        } else {
            return true;
        }
	}

	@Override
	public boolean dateIsBefore(String date1, String date2) {
		// TODO Auto-generated method stub
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date1).before(simpleDateFormat.parse(date2));
        } catch (ParseException e) {
            throw new InvalidRequestException(ErrorMessage.PARSE_ERROR);
        }
	}

	@Override
	public boolean bookingOverlaps(Booking bookings) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return bookingRepository.findAll().stream().anyMatch(dataBaseRes -> {
            //If two bookings have the same inventory id, then compare their check in and checkout dates
            if (dataBaseRes.getHotelId() == bookings.getHotelId()) {
                try {
                    int checkInBeforeDbCheckOut = sdf.parse(bookings.getCheckIn()).compareTo(sdf.parse(dataBaseRes.getCheckOut()));
                    int checkOutBeforeDbCheckIn = sdf.parse(bookings.getCheckOut()).compareTo(sdf.parse(dataBaseRes.getCheckIn()));
                    log.debug("check in int " + checkInBeforeDbCheckOut);
                    log.debug("check out int " + checkOutBeforeDbCheckIn);
                    if (checkInBeforeDbCheckOut == 0 || checkOutBeforeDbCheckIn == 0) {
                        return true;
                    } else {
                        return checkInBeforeDbCheckOut != checkOutBeforeDbCheckIn;
                    }
                } catch (ParseException e) {
                    throw new InvalidRequestException(ErrorMessage.PARSE_ERROR);
                }
            } else {
                return false;
            }

        });
	}

	@Override
	public boolean validateBookingExistence(Integer id) {
		// TODO Auto-generated method stub
		if(!bookingRepository.existsById(id)){
            throw new InvalidRequestException(ErrorMessage.INVALID_ID_EXISTENCE);
        } else {
            return true;
        }
	}
	
}
