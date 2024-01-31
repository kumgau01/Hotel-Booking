package com.mvp.hotel.booking.mvp_hotel_booking.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mvp.hotel.booking.mvp_hotel_booking.entities.Booking;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.Hotel;
import com.mvp.hotel.booking.mvp_hotel_booking.entities.ValidTypesOfHotelsEnum;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.BookingRepository;
import com.mvp.hotel.booking.mvp_hotel_booking.repository.HotelRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class HotelsAndBookingLoader implements CommandLineRunner {
	private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void run(String... args) throws Exception {

        if (hotelRepository.count() == 0) {
            log.info("Loading data from Hotels...");
            loadHotelObject();
        }

        if (bookingRepository.count() == 0) {
            log.info("Loading data from Bookings...");
            loadBookingObject();
        }


    }

    private void loadBookingObject() {
        Booking r1 = Booking.builder()
                .hotelId(1)
                .checkIn("2024-01-01")
                .checkOut("2024-01-31")
                .guests(2)
                .build();

        Booking r2 = Booking.builder()
                .hotelId(2)
                .checkIn("2024-02-01")
                .checkOut("2024-02-29")
                .guests(3)
                .build();

        Booking r3 = Booking.builder()
                .hotelId(3)
                .checkIn("2024-03-01")
                .checkOut("2024-03-31")
                .guests(4)
                .build();

        Booking r4 = Booking.builder()
                .hotelId(4)
                .checkIn("2024-04-01")
                .checkOut("2024-04-30")
                .guests(5)
                .build();

        Booking r5 = Booking.builder()
                .hotelId(5)
                .checkIn("2024-05-01")
                .checkOut("2024-05-31")
                .guests(6)
                .build();

        bookingRepository.save(r1);
        bookingRepository.save(r2);
        bookingRepository.save(r3);
        bookingRepository.save(r4);
        bookingRepository.save(r5);

        log.info("Loaded Bookings: " + bookingRepository.count());
    }

    private void loadHotelObject() {
        Hotel h1 = Hotel.builder()
                .name("Hotel Maurya")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("This 5 Star Hotel in Patna is located in Indira Nagar. Full Address of property is SOUTH GANDHI MAIDAN,FRAZER ROAD View On Map Key amenities of this property are Swimming Pool & Jacuzzi & Restaurant.")
                .availableFrom("2024-01-01")
                .availableTo("2024-01-31")
                .status(true)
                .build();

        Hotel h2 = Hotel.builder()
                .name("Lemon Tree Premier Patna")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("Indira Nagar | 7.3 km from Jay Prakash Narayan International Airport")
                .availableFrom("2024-02-01")
                .availableTo("2024-02-29")
                .status(true)
                .build();

        Hotel h3 = Hotel.builder()
                .name("The Red Velvet Hotel Samarpan")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .description("This 4 Star Hotel in Patna is located in Veer Chand Patel Path. Full Address of property is Income Tax Golamber, Kidwaipuri, Patna-1 View On Map Key amenities of this property are Restaurant & Fireplace.")
                .availableFrom("2024-03-01")
                .availableTo("2024-03-31")
                .status(true)
                .build();

        Hotel h4 = Hotel.builder()
                .name("Vijaya Tej Clarks Inn Patna")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("Patliputra Industrial Area | 6.0 km from Jay Prakash Narayan International Airport")
                .availableFrom("2024-04-01")
                .availableTo("2024-04-30")
                .status(true)
                .build();

        Hotel h5 = Hotel.builder()
                .name("The Panache")
                .type(ValidTypesOfHotelsEnum.LUXURY)
                .description("Indira Nagar | 7.4 km from Jay Prakash Narayan International Airport")
                .availableFrom("2024-05-01")
                .availableTo("2024-05-31")
                .status(true)
                .build();

        hotelRepository.save(h1);
        hotelRepository.save(h2);
        hotelRepository.save(h3);
        hotelRepository.save(h4);
        hotelRepository.save(h5);

        log.info("Loaded Hotels: " + hotelRepository.count());
    }
}
