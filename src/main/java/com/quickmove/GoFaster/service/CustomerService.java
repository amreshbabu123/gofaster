package com.quickmove.GoFaster.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookingHistoryDto;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.dto.RideDetailsDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Userr;
import com.quickmove.GoFaster.exception.BookingNotFoundException;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.repository.BookingRepository;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.repository.UserRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
	 @Autowired
	    private CustomerRepository customerRepo;
	 @Autowired
	 private DriverRepository driverRepo;
	 @Autowired
	    private UserRepository userRepo;
	 @Autowired
	    private LocationIQService locationIQService;
	 @Autowired
	 private BookingRepository bookingRepository;
	 @Autowired
     private PasswordEncoder passwordEncoder;

	 @Transactional
	 public ResponseEntity<ResponseStructure<Customer>> registerCustomer(CustomerDto customerDto) {
		 
		 if (customerRepo.findByMobileNo(customerDto.getMobileNo()).isPresent()) {
		        throw new RuntimeException("Mobile number already registered");
		    }

	     Userr user = new Userr();
	     user.setMobileno(customerDto.getMobileNo());
	     user.setRole("CUSTOMER");
	     user.setPassword(passwordEncoder.encode(customerDto.getPassword()));

	     Userr savedUser = userRepo.save(user);

	     Customer c = new Customer();
	     c.setName(customerDto.getName());
	     c.setAge(customerDto.getAge());
	     c.setGender(customerDto.getGender());
	     c.setMobileNo(customerDto.getMobileNo());
	     c.setEmailId(customerDto.getEmailId());
	     c.setLatitude(customerDto.getLatitude());
	     c.setLongitude(customerDto.getLongitude());
	     
	     String currentCity = locationIQService.getCityFromCoordinates(customerDto.getLatitude(), customerDto.getLongitude());
	     c.setCurrentLocation(currentCity);
	     c.setUser(savedUser);

	     customerRepo.save(c);

	     ResponseStructure<Customer> response = new ResponseStructure<>();
	     response.setStatuscode(HttpStatus.CREATED.value());
	     response.setMessage("Customer registered successfully");
	     response.setData(c);

	     return new ResponseEntity<>(response, HttpStatus.CREATED);
	 }

	 
	 
	 
    public ResponseEntity<ResponseStructure<Customer>> findByMobile(long mobileNo) {

    	Customer c = customerRepo
    	        .findByMobileNo(mobileNo)
    	        .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		    ResponseStructure<Customer> response = new ResponseStructure<>();
		    response.setStatuscode(HttpStatus.OK.value());
		    response.setMessage("Customer found successfully");
		    response.setData(c);

		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	    
	    
    
	public ResponseEntity<ResponseStructure<Customer>> deleteByMobile(long mobileNo) {

		Customer c = customerRepo
    	        .findByMobileNo(mobileNo)
    	        .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		    customerRepo.delete(c);

		    ResponseStructure<Customer> response = new ResponseStructure<>();
		    response.setStatuscode(HttpStatus.OK.value());
		    response.setMessage("Customer deleted successfully");
		    response.setData(c);

		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	
	
	
	public ResponseEntity<ResponseStructure<BookingHistoryDto>> getCustomerBookingHistoryByMobile(Long mobileNo) {

		Customer customer = customerRepo
    	        .findByMobileNo(mobileNo)
    	        .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        List<Booking> bookings = customer.getBookingList();

        if (bookings == null || bookings.isEmpty()) {
            throw new BookingNotFoundException("No booking history found");
        }

        List<RideDetailsDto> history = new ArrayList<>();
        double totalAmount = 0;

        for (Booking booking : bookings) {

           
            if (!"COMPLETED".equalsIgnoreCase(booking.getBookingStatus())) {
                continue;
            }

            RideDetailsDto ride = new RideDetailsDto();
            ride.setFromLocation(booking.getSourceLocation());
            ride.setToLocation(booking.getDestinationLocation());
            ride.setDistance(booking.getDistanceTravelled());
            ride.setFare(booking.getFare());

            history.add(ride);
            totalAmount += booking.getFare();
        }

        BookingHistoryDto dto = new BookingHistoryDto();
        dto.setHistory(history);
        dto.setToatalAmount(totalAmount);

        ResponseStructure<BookingHistoryDto> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Customer booking history fetched successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

	
	
	public ResponseEntity<ResponseStructure<Customer>> cancelRideByCustomer(
	        int bookingId, int custId) {

	    Customer customer = customerRepo.findById((long) custId)
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    Booking booking = bookingRepository.findById((long) bookingId)
	            .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

	    // 🔐 SECURITY CHECK
	    if (!booking.getCustomer().getId().equals(customer.getId())) {
	        throw new RuntimeException("Booking does not belong to this customer");
	    }

	    if ("CANCELLED_BY_CUSTOMER".equalsIgnoreCase(booking.getBookingStatus())) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    // ✅ CANCEL BOOKING
	    booking.setBookingStatus("CANCELLED_BY_CUSTOMER");

	    // ✅ DRIVER + VEHICLE UPDATE
	    Driver driver = booking.getDriver();
	    if (driver != null) {
	        driver.setStatus("Available");
	        if (driver.getVehicle() != null) {
	            driver.getVehicle().setVehicleavailabilityStatus("Available");
	        }
	        driverRepo.save(driver);
	    }

	    // ✅ PENALTY (ONLY ONCE)
	    customer.setPenalty(customer.getPenalty() + 1);

	    bookingRepository.save(booking);
	    customerRepo.save(customer);

	    ResponseStructure<Customer> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.OK.value());
	    response.setMessage("Booking cancelled. Penalty applied.");
	    response.setData(customer);

	    return ResponseEntity.ok(response); 
	}

}