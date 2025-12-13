package com.quickmove.GoFaster.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookVehicleDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
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

    public ResponseEntity<ResponseStructure<Booking>> bookVehicle(
            BookVehicleDto bookVehicleDto) {

    	Customer customer = customerRepo.findByMobileNo(
    	        Long.parseLong(bookVehicleDto.getCustomerMobileNo()));

    	if (customer == null) {
    	    throw new CustomerNotFoundException("Customer not found");
    	}

    	Driver driver = driverRepo.findByMobileNo(
    	        bookVehicleDto.getDriverMobileNo());
    	
    	if (driver == null) {
    	    throw new DriverMobileNoNotFound("Driver not found");
    	}


            Booking booking = new Booking();
            String status=driver.getStatus();
            System.err.println(status);
            
            if(status.equalsIgnoreCase("booked")) {
            	throw new DriverMobileNoNotFound(status);
            }
            booking.setCustomer(customer);
            booking.setDriver(driver);
            booking.setSourceLocation(bookVehicleDto.getSourceLocation());
            booking.setDestinationLocation(bookVehicleDto.getDestinationLocation());
            booking.setBookingDate(LocalDateTime.now());
            booking.setDistanceTravelled(10.0);
            booking.setFare(500.0);

            
            customer.getBookingList().add(booking);
            driver.getBookingList().add(booking);

            bookingRepo.save(booking);
            
            driver.setStatus("booked");
            driver.getVehicle().setVehicleavailabilityStatus("booked");

           
            customerRepo.save(customer);
            driverRepo.save(driver);


            ResponseStructure<Booking> response = new ResponseStructure<>();
            response.setStatuscode(HttpStatus.CREATED.value());
            response.setMessage("Booking created successfully");
            response.setData(booking);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
             
            
        }


		public ResponseStructure<Booking> activeBooking(long mobileNo) {
			ResponseStructure<Booking> structure = new ResponseStructure<>();

		    Booking active = bookingRepo.findActiveBooking(mobileNo);

		    if (active == null) {
		        structure.setStatuscode(404);
		        structure.setMessage("No active booking found for this customer");
		        structure.setData(null);
		        return structure;
		    }

		    structure.setStatuscode(200);
		    structure.setMessage("Active booking fetched successfully");
		    structure.setData(active);

		    return structure;
			
		}


		public ResponseStructure<Booking> driverActiveBooking(long mobileNo) {
			ResponseStructure<Booking> structure = new ResponseStructure<>();

		    Booking active = bookingRepo.findDriverActiveBooking(mobileNo);

		    if (active == null) {
		        structure.setStatuscode(404);
		        structure.setMessage("No active booking found for this customer");
		        structure.setData(null);
		        return structure;
		    }

		    structure.setStatuscode(200);
		    structure.setMessage("Active booking fetched successfully");
		    structure.setData(active);

		    return structure;
			
		
			
		}
    
    	
}
