package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class CustomerService {
	 @Autowired
	    private CustomerRepository customerRepo;

	    public ResponseStructure<Customer> register(CustomerDto dto) {

	        Customer c = new Customer();
	        c.setName(dto.getName());
	        c.setAge(dto.getAge());
	        c.setGender(dto.getGender());
	        c.setMobileNo(dto.getMobileNo());
	        c.setEmailId(dto.getEmailId());
	        c.setLatitude(dto.getLatitude());
	        c.setLongitude(dto.getLongitude());
            c.setCurrentLocation("hyderabad");
	        customerRepo.save(c);

	        return new ResponseStructure<>(
	                HttpStatus.CREATED.value(),
	                "Customer registered successfully",
	                c
	        );
	    }

	    public ResponseStructure<Customer> findByMobile(long mobileNo) {

	        Customer c = customerRepo.findByMobileNo(mobileNo);

	        if (c == null) {
	            throw new CustomerNotFoundException("Customer not found: " + mobileNo);
	        }

	        return new ResponseStructure<>(
	                HttpStatus.OK.value(),
	                "Customer found",
	                c
	        );
	    }

	    public ResponseStructure<String> deleteByMobile(long mobileNo)  {

	        Customer c = customerRepo.findByMobileNo(mobileNo);

	        if (c == null)
	            throw new CustomerNotFoundException("Customer not found with mobile: " + mobileNo);

	        customerRepo.delete(c);

	        return new ResponseStructure<>(
	                HttpStatus.OK.value(),
	                "Customer deleted successfully",
	                "deleted"
	        );
	    }


}