package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;     
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.BookingHistoryDto;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.service.CustomerService;
import com.quickmove.GoFaster.util.ResponseStructure;

@RestController
public class CustomerController {
	
	    @Autowired 
	    private CustomerService customerService;
	    

	    @GetMapping("/findcustomerwithmobileno")
	    public ResponseEntity<ResponseStructure<Customer>> findCustomer(@RequestParam long mobileNo)  {
	        return customerService.findByMobile(mobileNo);
	    }

	    @DeleteMapping("/deletecustomer/{mobileNo}")
	    public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@PathVariable long mobileNo) {
	        return customerService.deleteByMobile(mobileNo);
	    }
	    
	    
	    @GetMapping("/customer/seeallbookinghistorywithmobileno")
	    public ResponseEntity<ResponseStructure<BookingHistoryDto>>getCustomerBookingHistory(@RequestParam Long mobileNo) {
            return customerService.getCustomerBookingHistoryByMobile(mobileNo);
	    }
	    
	    @PostMapping("/customer/cancelbooking")
	    public ResponseEntity<ResponseStructure<Customer>> cancelridebydriver(@RequestParam int bookingId,@RequestParam int custId){
			return customerService.cancelRideByCustomer(bookingId,custId);
	    	
	    }
}
