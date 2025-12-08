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

	    public Customer register(CustomerDto customerDto) {
	        Customer c = new Customer();
	        c.setName(customerDto.getName());
	        c.setAge(customerDto.getAge());
	        c.setGender(customerDto.getGender());
	        c.setMobileNo(customerDto.getMobileNo());
	        c.setEmailId(customerDto.getEmailId());
	        
	        customerRepo.save(c);
	        return c;
	    }

	    


		public boolean deleteByMobile(long mobileNo) {
			 Customer c = customerRepo.findByMobileNo(mobileNo);
		        if (c == null) return false;
		        customerRepo.delete(c);
		        return true;

		}




		public Customer findByMobile(long mobileNo) {
			  return customerRepo.findByMobileNo(mobileNo);

		}


}