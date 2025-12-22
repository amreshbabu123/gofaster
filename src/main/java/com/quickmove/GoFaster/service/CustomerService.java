package com.quickmove.GoFaster.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookingHistoryDto;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.dto.RideDetailsDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.exception.BookingNotFoundException;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class CustomerService {
	 @Autowired
	    private CustomerRepository customerRepo;
	 @Autowired
	 private DriverRepository driverRepo;

	 public ResponseEntity<ResponseStructure<Customer>> registerCustomer(CustomerDto customerDto)  {

	        Customer c = new Customer();
	        c.setName(customerDto.getName());
	        c.setAge(customerDto.getAge());
	        c.setGender(customerDto.getGender());
	        c.setMobileNo(customerDto.getMobileNo());
	        c.setEmailId(customerDto.getEmailId());
	        c.setLatitude(customerDto.getLatitude());
	        c.setLongitude(customerDto.getLongitude());
            c.setCurrentLocation("hyderabad");
	        customerRepo.save(c);
	        
	        ResponseStructure<Customer> response = new ResponseStructure<Customer>();
	        response.setStatuscode(HttpStatus.CREATED.value());
	        response.setMessage("Customer registered successfully");
	        response.setData(c);
	        
	        return new  ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.CREATED);
	    }    
	 
	 
	 
    public ResponseEntity<ResponseStructure<Customer>> findByMobile(long mobileNo) {

		    Customer c = customerRepo.findByMobileNo(mobileNo);

		    if (c == null) {
		        throw new CustomerNotFoundException("Customer not found with mobile: " + mobileNo);
		    }

		    ResponseStructure<Customer> response = new ResponseStructure<>();
		    response.setStatuscode(HttpStatus.OK.value());
		    response.setMessage("Customer found successfully");
		    response.setData(c);

		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	    
	    
    
	public ResponseEntity<ResponseStructure<Customer>> deleteByMobile(long mobileNo) {

		    Customer c = customerRepo.findByMobileNo(mobileNo);

		    if (c == null) {
		        throw new CustomerNotFoundException(
		                "Customer not found with mobile: " + mobileNo);
		    }

		    customerRepo.delete(c);

		    ResponseStructure<Customer> response = new ResponseStructure<>();
		    response.setStatuscode(HttpStatus.OK.value());
		    response.setMessage("Customer deleted successfully");
		    response.setData(c);

		    return new ResponseEntity<>(response, HttpStatus.OK);
		}

	
	
	
	public ResponseEntity<ResponseStructure<BookingHistoryDto>> getCustomerBookingHistoryByMobile(Long mobileNo) {

		Customer customer = customerRepo.findByMobileNo(mobileNo);
		if (customer == null) {
		    throw new CustomerNotFoundException("Customer not found");
		}

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


	public ResponseEntity<ResponseStructure<Customer>> cancelRideByCustomer(int bookingId, int custId) {

	    Optional<Customer> optionalCustomer = customerRepo.findById((long) custId);
	    if (optionalCustomer.isEmpty()) {
	        throw new RuntimeException("Customer not found");
	    }

	    Customer customer = optionalCustomer.get();
	   
	    Booking bookingToCancel = null;
	    for (Booking booking : customer.getBookingList()) {
	        if (booking.getId() == bookingId) {
	            bookingToCancel = booking;
	            break;
	        }
	    }

	    if (bookingToCancel == null) {
	        throw new BookingNotFoundException("Booking not found for this customer");
	    }

	   
	    if ("CANCELLED_BY_CUSTOMER".equalsIgnoreCase(bookingToCancel.getBookingStatus())) {
	        throw new BookingNotFoundException("Booking already cancelled");
	    }

	   
	    bookingToCancel.setBookingStatus("CANCELLED_BY_CUSTOMER");
	    
	 // 5. Update DRIVER + VEHICLE status
	    Driver driver = bookingToCancel.getDriver();
	    driver.setStatus("available");

	    if (driver.getVehicle() != null) {
	        driver.getVehicle().setVehicleavailabilityStatus("available");
	    }
	    
	    


	    // 6. Increase penalty COUNT only
	    customer.setPenalty(customer.getPenalty() + 1);

	    // 7. Save customer (booking saved via cascade)
	  
	    customer.setPenalty(customer.getPenalty() + 1);

	  

	    customerRepo.save(customer);
	    driverRepo.save(driver); 


	 // 8. Response
	    ResponseStructure<Customer> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.OK.value());
	    response.setMessage("Booking cancelled. Driver is now available. Penalty increased.");
	    response.setData(customer);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}