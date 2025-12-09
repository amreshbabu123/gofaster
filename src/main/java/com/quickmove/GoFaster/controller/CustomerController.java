package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.service.CustomerService;
import com.quickmove.GoFaster.util.ResponseStructure;
@RestController
public class CustomerController {
	 @Autowired 
	    private CustomerService customerService;

	    @PostMapping("/registercustomer")
	    public ResponseStructure<Customer> register(@RequestBody CustomerDto customerDto) {
	        return customerService.register(customerDto);
	    }

	    @GetMapping("/findcustomerwithmobileno")
	    public ResponseStructure<Customer> findCustomer(@RequestParam long mobileNo)  {
	        return customerService.findByMobile(mobileNo);
	    }

	    @DeleteMapping("/deletecustomer/{mobileNo}")
	    public ResponseStructure<String> deleteCustomer(@PathVariable long mobileNo) {
	        return customerService.deleteByMobile(mobileNo);
	    }


    




}
