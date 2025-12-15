package com.quickmove.GoFaster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookingHistoryDto;
import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.dto.RideDetailsDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class CustomerService {
	 @Autowired
	    private CustomerRepository customerRepo;

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
		    throw new RuntimeException("Customer not found");
		}

        List<Booking> bookings = customer.getBookingList();

        if (bookings == null || bookings.isEmpty()) {
            throw new RuntimeException("No booking history found");
        }

        List<RideDetailsDto> history = new ArrayList<>();
        double totalAmount = 0;

        for (Booking booking : bookings) {

            // Optional: show only COMPLETED rides
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
}