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
import com.quickmove.GoFaster.service.CustomerService;
import com.quickmove.GoFaster.util.ResponseStructure;
@RestController
public class CustomerController {
	@Autowired 
	private CustomerService customerService;

    @PostMapping("/register")
    public ResponseStructure<Customer> register(@RequestBody CustomerDto dto) {
        Customer c = customerService.register(dto);
        return new ResponseStructure<>(HttpStatus.CREATED.value(), "Customer registered", c);
    }
    
    @GetMapping("/find")
    public ResponseStructure<Customer> findCustomer(@RequestParam long mobileNo) {
        Customer c = customerService.findByMobile(mobileNo);
        if (c == null) return new ResponseStructure<>(HttpStatus.NOT_FOUND.value(), "Not found", null);
        return new ResponseStructure<>(HttpStatus.FOUND.value(), "Customer found", c);
    }
    
   
    @DeleteMapping("/delete/{mobileNo}")
    public ResponseStructure<String> deleteCustomer(@PathVariable long mobileNo){
    	boolean ok = customerService.deleteByMobile(mobileNo);
    	if (!ok) return new ResponseStructure<>(HttpStatus.NOT_FOUND.value(), "Customer not found", null);
    	return new ResponseStructure<>(HttpStatus.OK.value(), "Customer deleted", "deleted");
    	
    }
    




}
