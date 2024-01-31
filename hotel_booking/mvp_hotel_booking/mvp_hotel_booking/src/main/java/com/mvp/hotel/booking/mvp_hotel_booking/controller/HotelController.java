package com.mvp.hotel.booking.mvp_hotel_booking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mvp.hotel.booking.mvp_hotel_booking.constants.AppConstants;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.IdEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.dto.SuccessEntity;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;
import com.mvp.hotel.booking.mvp_hotel_booking.service.HotelService;
import com.mvp.hotel.booking.mvp_hotel_booking.validators.HotelValidator;
import com.mvp.hotel.booking.mvp_hotel_booking.validators.PageNumberAndSizeValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HotelController {
	private final HotelService hotelService;
	
    @GetMapping(value = "/hotels", produces = "application/json")
    public ResponseEntity<List<Hotel>> getHotelList(){
        log.info("Get all: {} hotels from database", hotelService.getAllHotels().size());
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
    
    @GetMapping(value = "/hotelPagedList", produces = "application/json")
    public ResponseEntity<List<Hotel>> getPagedHotelList(
            @RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORTING_PARAM) String sortBy) {

        PageNumberAndSizeValidator.validatePageNumberAndSize(pageNumber, pageSize);
        List<Hotel> hotelPagedList = hotelService.getHotelPagedList(pageNumber, pageSize, sortBy);

        log.info("Return Hotel paged list with pageNumber: {}, pageSize: {} and sortBy: {}.", pageNumber, pageSize, sortBy);

        return new ResponseEntity<>(hotelPagedList, HttpStatus.OK);
    }
    
    @GetMapping(value = "/hotel/{id}", produces = "application/json")
    public Hotel getHotel(@PathVariable Integer id) {
        HotelValidator.validateId(id);
        log.info("Get hotel by id = {}", id);
        return hotelService.getHotel(id);
    }

    @GetMapping(value = "/hotels/availabilitySearch", produces = "application/json")
    public List<Hotel> getHotel(@RequestParam("dateFrom") String from, @RequestParam("dateTo") String to){
        HotelValidator.validateDates(from, to);
        log.info("Get all Hotels available between dates from: {} to: {}", from, to);
        return hotelService.getAvailable(from, to);
    }

    @PatchMapping(value = "/hotel", produces = "application/json")
    public SuccessEntity patchHotel(@RequestBody @Valid Hotel hotel){
        HotelValidator.validateHotelPATCH(hotel);
        log.info("Update Hotel with name: {}", hotel.getName());
        return hotelService.patchHotel(hotel);
    }

    @PostMapping(value = "/hotel", produces = "application/json")
    public IdEntity saveHotel(@RequestBody @Valid Hotel hotel){
        HotelValidator.validateHotelPOST(hotel);
        log.info("Save a user specified hotel with name: {}", hotel.getName());
        return hotelService.saveHotel(hotel);
    }

    @DeleteMapping(value = "/hotel/{id}", produces = "application/json")
    public SuccessEntity deleteHotel(@PathVariable Integer id){
        HotelValidator.validateId(id);
        log.info("Delete a user specified hotel with id = {}", id);
        return hotelService.deleteHotel(id);
    }
}
