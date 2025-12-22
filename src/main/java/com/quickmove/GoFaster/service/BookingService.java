package com.quickmove.GoFaster.service;

import java.time.LocalDateTime; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookVehicleDto;
import com.quickmove.GoFaster.dto.ORSDistanceResponse;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
import com.quickmove.GoFaster.exception.DriverNotFoundException;
import com.quickmove.GoFaster.repository.BookingRepository;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class BookingService {

    @Autowired 
    private CustomerRepository customerRepo;

    @Autowired 
    private DriverRepository driverRepo;

    @Autowired 
    private BookingRepository bookingRepo;
    
    @Autowired
    private ORSService orsService;

    @Autowired
    private LocationIQService locationIQ;


    public ResponseEntity<ResponseStructure<Booking>> bookVehicle(BookVehicleDto bookVehicleDto) {

    	Customer customer = customerRepo.findByMobileNo(bookVehicleDto.getCustomerMobileNo());

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        Driver driver = driverRepo.findByMobileNo(bookVehicleDto.getDriverMobileNo());

        if (driver == null) {
            throw new DriverMobileNoNotFound("Driver not found");
        }

        if ("booked".equalsIgnoreCase(driver.getStatus())) {
            throw new DriverNotFoundException("Driver is already booked");
        }
        
  
        double[] src = locationIQ.getCoordinates(bookVehicleDto.getSourceLocation());
        double[] dst = locationIQ.getCoordinates(bookVehicleDto.getDestinationLocation());

       
        ORSDistanceResponse ors = orsService.getDistance(
                src[0], src[1],
                dst[0], dst[1]
        );

       
        double distanceKm = Math.round(ors.getDistanceKm() * 100.0) / 100.0;
        double estimatedTimeHrs = Math.round(ors.getTimeHours() * 100.0) / 100.0;
       
        
        double baseFare = driver.getVehicle().getPricePerKm() * distanceKm;

  
        int penaltyCount = customer.getPenalty().intValue(); 

        double penaltyPercent = penaltyCount * 10;     

        double finalFare = Math.round(
                (baseFare + (baseFare * penaltyPercent / 100)) * 100.0
        ) / 100.0;

        // 5️⃣ OTP GENERATION (CUSTOMER ONLY)
        // ============================
        int otp = 1000 + new java.util.Random().nextInt(9000); // 4-digit OTP



        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDriver(driver);
        booking.setVehicle(driver.getVehicle()); 
        booking.setSourceLocation(bookVehicleDto.getSourceLocation());
        booking.setDestinationLocation(bookVehicleDto.getDestinationLocation());
        booking.setBookingDate(LocalDateTime.now());

        booking.setDistanceTravelled(distanceKm);  
        booking.setEstimatedTimeRequired(estimatedTimeHrs);
        booking.setFare(finalFare);   
        
        booking.setBookingStatus("ACTIVE");
        booking.setPaymentStatus("NOT_PAID");
        
        booking.setDeliveryOtp(otp); // 🔐 stored, NOT shared

        customer.getBookingList().add(booking);
        driver.getBookingList().add(booking);

        bookingRepo.save(booking);

        driver.setStatus("booked");
        driver.getVehicle().setVehicleavailabilityStatus("booked");

        customerRepo.save(customer);
        driverRepo.save(driver);
        
     // 📧 SEND MAIL AFTER BOOKING
        // ============================

        String subject = "Booking Confirmed | Booking ID: " + booking.getId();

        String message =
                "Dear " + customer.getName() + ",\n\n" +
                "Your ride has been successfully booked.\n\n" +
                "📍 Source: " + booking.getSourceLocation() + "\n" +
                "📍 Destination: " + booking.getDestinationLocation() + "\n" +
                "🚗 Driver: " + driver.getName() + "\n" +
                "🚘 Vehicle: " + driver.getVehicle().getVehicleType() + "\n" +
                "📏 Distance: " + distanceKm + " km\n" +
                "⏱ Estimated Time: " + estimatedTimeHrs + " hrs\n" +
                "💰 Fare: ₹" + finalFare + "\n\n" +
                "🔐 Your Ride OTP: " + otp + "\n\n" +
                "⚠️ Please share this OTP verbally with the driver.\n" +
                "Do NOT share it in chat or screenshots.\n\n" +
                "— QuickMove Support Team";
        
        // mailer.sendMail(customer.getEmail(), subject, message);
        mailer.sendMail("boyaramanjaneyulu665@gmail.com", subject, message);

        ResponseStructure<Booking> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.CREATED.value());
        response.setMessage("Booking created successfully");
        response.setData(booking);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    
    

    public ResponseEntity<ResponseStructure<Booking>> customeractiveBooking(long mobileNo) {

        ResponseStructure<Booking> structure = new ResponseStructure<>();

        Booking active = bookingRepo.findActiveBooking(mobileNo);

        if (active == null) {
            structure.setStatuscode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("No active booking found for this customer");
            structure.setData(null);

            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }

        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage("Active booking fetched successfully");
        structure.setData(active);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }


		
		


    public ResponseEntity<ResponseStructure<Booking>> driverActiveBooking(long mobileNo) {

        ResponseStructure<Booking> structure = new ResponseStructure<>();

        Booking active = bookingRepo.findDriverActiveBooking(mobileNo);

        if (active == null) {
            structure.setStatuscode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("No active booking found for this driver");
            structure.setData(null);

            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }

        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage("Active booking fetched successfully");
        structure.setData(active);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }



    @Autowired
    private MailService mailer;
	public void sendingMail() {
		// TODO Auto-generated method stub
		
		mailer.sendMail(
			    "boyaramanjaneyulu665@gmail.com",
			    "Booking Confirmed",
			    "Your booking has been successfully confirmed."
			);

	}
}
