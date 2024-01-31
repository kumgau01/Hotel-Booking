package com.mvp.hotel.booking.mvp_hotel_booking;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@RequestMapping("/gaurav")
	public String check() {
		//System.out.println("HI GAURAV");
		return "Gaurav Will listen..";
	}

}
