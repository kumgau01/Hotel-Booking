package com.mvp.hotel.booking.mvp_hotel_booking.serviceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.ErrorMessage;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;
import com.mvp.hotel.booking.mvp_hotel_booking.exceptions.InvalidRequestException;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.BookingRepository;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.HotelRepository;
import com.mvp.hotel.booking.mvp_hotel_booking.service.HotelService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImp implements HotelService {
	private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getHotelPagedList(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sortBy);
        Page<Hotel> pagedResult = hotelRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Hotel getHotel(Integer id) {
        validateHotelExistenceById(id);
        return hotelRepository.findById(id).get();
    }

    @Override
    public List<Hotel> getAvailable(String dateFrom, String dateTo) {
        return hotelRepository.findAllBetweenDates(dateFrom, dateTo);
    }

    @Override
    public IdEntity saveHotel(@Valid Hotel hotel) {
        if ((!StringUtils.hasText(hotel.getAvailableFrom())) && (!(StringUtils.hasText(hotel.getAvailableTo())))) {
            hotel.setAvailableFrom(null);
            hotel.setAvailableTo(null);
        }
        hotel = hotelRepository.save(hotel);
        IdEntity idEntity = new IdEntity();
        idEntity.setId(hotel.getId());
        return idEntity;
    }

    @Override
    public SuccessEntity deleteHotel(Integer id) {
        validateHotelExistenceById(id);
        if (bookingRepository.findAll().stream()
                .anyMatch(bookings -> bookings.getHotelId().equals(id))) {
            throw new InvalidRequestException(ErrorMessage.INVALID_HOTEL_DELETE);
        }
        SuccessEntity successEntity = new SuccessEntity();
        hotelRepository.deleteById(id);
        successEntity.setSuccess(!hotelRepository.existsById(id));
        return successEntity;
    }

    @Override
    public SuccessEntity patchHotel(Hotel hotel) {
        validateHotelExistenceById(hotel.getId());
        doesBookingOverlap(hotel);
        SuccessEntity successEntity = new SuccessEntity();
        hotel = hotelRepository.save(hotel);
        successEntity.setSuccess(hotelRepository.existsById(hotel.getId()));
        return successEntity;
    }

    @Override
    public void doesBookingOverlap(Hotel hotel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String availTo = hotel.getAvailableTo();
        String availFrom = hotel.getAvailableFrom();
        Integer hotelId = hotel.getId();
        List<Booking> matchingBookingList = bookingRepository.findAll().stream().filter(bookings -> {
            if (bookings.getHotelId() == hotelId) {
                try {
                    //Checks to see if the user dates are null, if so throw an error as it conflicts with a booking
                    if (!StringUtils.hasText(availTo) && !StringUtils.hasText(availFrom)) {
                        throw new InvalidRequestException(ErrorMessage.INVALID_DATE_CHANGE_NULL);
                    }
                    //should return 1 or 0 if there is no overlap, should return -1 if there is an overlap
                    int checkInBeforeAvailFrom = sdf.parse(bookings.getCheckIn()).compareTo(sdf.parse(availFrom));
                    //should return -1 or 0 if there is no overlap, should return 1 if there is an overlap
                    int checkOutBeforeAvailTo = sdf.parse(bookings.getCheckOut()).compareTo(sdf.parse(availTo));
                    if ((checkInBeforeAvailFrom < 0) || (checkOutBeforeAvailTo > 0)) {
                        return true;
                    }

                } catch (ParseException e) {
                    throw new InvalidRequestException(ErrorMessage.PARSE_ERROR);
                }
            }
            return false;
        }).toList();

        if (matchingBookingList.size() != 0) {
            throw new InvalidRequestException(ErrorMessage.INVALID_HOTEL_UPDATE);
        }
    }
    
    @Override
    public boolean validateHotelExistenceById(Integer id) {
        if (!hotelRepository.existsById(id)) {
            log.error("Invalid ID: The entered id = {} does not exist.", id);
            throw new InvalidRequestException(ErrorMessage.INVALID_ID_EXISTENCE);
        } else {
            return true;
        }
    }
}
