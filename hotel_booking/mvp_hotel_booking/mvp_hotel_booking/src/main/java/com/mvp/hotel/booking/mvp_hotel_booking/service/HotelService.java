package com.mvp.hotel.booking.mvp_hotel_booking.service;

import java.util.List;

import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;

public interface HotelService {
	List<Hotel> getHotelPagedList(Integer pageNo, Integer pageSize, String sortBy); // Pagination
	List<Hotel> getAllHotels();
	Hotel getHotel(Integer id);
	List<Hotel> getAvailable(String dateFrom, String dateTo);
	IdEntity saveHotel(Hotel hotel);
	SuccessEntity deleteHotel(Integer id);
	SuccessEntity patchHotel(Hotel hotel);
	void doesBookingOverlap(Hotel hotel);
	boolean validateHotelExistenceById(Integer id);
}
