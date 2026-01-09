package com.quickmove.GoFaster.service;

import java.time.LocalDateTime;   
import java.util.List;
import java.util.Optional;

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
    
    @Autowired
    private MailService mailer;


    public ResponseEntity<ResponseStructure<Booking>> bookVehicle(BookVehicleDto bookVehicleDto) {

    	Customer customer = customerRepo
    	        .findByMobileNo(bookVehicleDto.getCustomerMobileNo())
    	        .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));


    	Driver driver = driverRepo
    	        .findByMobileNo(bookVehicleDto.getDriverMobileNo())
    	        .orElseThrow(() -> new DriverMobileNoNotFound("Driver not found"));


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
        
        mailer.sendMail(customer.getEmailId(), subject, message);

        ResponseStructure<Booking> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.CREATED.value());
        response.setMessage("Booking created successfully");
        response.setData(booking);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    
    

    public ResponseEntity<ResponseStructure<Booking>> customeractiveBooking(long mobileNo) {

        ResponseStructure<Booking> structure = new ResponseStructure<>();

        List<Booking> activeBookings = bookingRepo.findActiveBookings(mobileNo);

        if (activeBookings.isEmpty()) {
            structure.setStatuscode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("No active booking found");
            structure.setData(null);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }

        // Take latest booking
        Booking latestBooking = activeBookings.get(0);

        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage("Active booking fetched successfully");
        structure.setData(latestBooking);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }



		
		


    public ResponseEntity<ResponseStructure<Booking>> getDriverActiveBooking(long driverMobileNo) {

        Driver driver = driverRepo.findByMobileNo(driverMobileNo)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Optional<Booking> optionalBooking =
                bookingRepo.findDriverActiveBooking(driverMobileNo);

        ResponseStructure<Booking> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());

        if (optionalBooking.isEmpty()) {
            response.setMessage("No active ride");
            response.setData(null);
        } else {
            response.setMessage("Active ride found");
            response.setData(optionalBooking.get());
        }

        return ResponseEntity.ok(response);
    }

    /* ================= ACCEPT BOOKING ================= */
    public ResponseEntity<ResponseStructure<Booking>> acceptBooking(
            long bookingId, long driverMobileNo) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Driver driver = driverRepo.findByMobileNo(driverMobileNo)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        booking.setDriver(driver);
        booking.setBookingStatus("ACCEPTED");

        bookingRepo.save(booking);

        ResponseStructure<Booking> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Ride accepted");
        response.setData(booking);

        return ResponseEntity.ok(response);
    }
    
    
    public ResponseEntity<ResponseStructure<Booking>> completeRideByDriver(
            long driverMobileNo,
            long bookingId) {

        ResponseStructure<Booking> structure = new ResponseStructure<>();

        Driver driver = driverRepo.findByMobileNo(driverMobileNo)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        /* 🔐 SECURITY CHECK */
        if (booking.getDriver() == null ||
            booking.getDriver().getId() != driver.getId()) {
            throw new RuntimeException("This ride does not belong to this driver");
        }

        /* ✅ STATUS CHECK */
        if (!booking.getBookingStatus().equals("ACTIVE") &&
            !booking.getBookingStatus().equals("ONGOING")) {
            throw new RuntimeException("Ride is not active");
        }

        /* ✅ COMPLETE RIDE */
        booking.setBookingStatus("COMPLETED");

        bookingRepo.save(booking);

        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage("Ride completed successfully");
        structure.setData(booking);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
    
    
    
    public ResponseEntity<ResponseStructure<Booking>> getDriverPendingPayment(long mobileNo) {
    	Booking booking = bookingRepo.findCompletedUnpaidBooking(mobileNo)
                .orElse(null);
	     ResponseStructure<Booking> rs = new ResponseStructure<>();
	     rs.setStatuscode(HttpStatus.OK.value());
	     rs.setMessage("Pending payment booking");
	     rs.setData(booking);
	     return ResponseEntity.ok(rs);
    }
    

    public ResponseEntity<ResponseStructure<Booking>> startRideWithOtp(
            long bookingId, String otp) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getBookingStatus().equals("ACCEPTED")) {
            throw new RuntimeException("Ride cannot be started now");
        }
        // ✅ Convert DB OTP to String and trim both sides
        if (!String.valueOf(booking.getDeliveryOtp()).trim().equals(otp.trim())) {
            throw new RuntimeException("Invalid OTP");
        }

        booking.setBookingStatus("ONGOING");
        bookingRepo.save(booking);

        ResponseStructure<Booking> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("OTP verified. Ride started.");
        response.setData(booking);

        return ResponseEntity.ok(response);
    }
}
