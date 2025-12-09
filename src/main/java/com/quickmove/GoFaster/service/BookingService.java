package com.quickmove.GoFaster.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookVehicleDto;
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


        public ResponseStructure<Booking> bookVehicle(BookVehicleDto bookVehicleDto) throws DriverNotFoundException, CustomerNotFoundException {

            Customer customer = customerRepo.findByMobileNo(Long.parseLong(bookVehicleDto.getCustomerMobileNo()));
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found with mobile: " + bookVehicleDto.getCustomerMobileNo());
            }

            Driver driver = driverRepo.findByMobileNo(bookVehicleDto.getDriverMobileNo());
            if (driver == null) {
            	
            	throw new DriverMobileNoNotFound();
            }

            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setDriver(driver);
            booking.setSourceLocation(bookVehicleDto.getSourceLocation());
            booking.setDestinationLocation(bookVehicleDto.getDestinationLocation());
            booking.setBookingDate(LocalDateTime.now());
            booking.setDistanceTravelled(10.0);
            booking.setFare(500.0);

            bookingRepo.save(booking);

            return new ResponseStructure<>(201, "Booking created successfully", booking);
             
             
            
        }
    

    


}
