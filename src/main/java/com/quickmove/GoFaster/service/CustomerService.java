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

	    public Customer register(CustomerDto dto) {
	        Customer c = new Customer();
	        c.setName(dto.getName());
	        c.setAge(dto.getAge());
	        c.setGender(dto.getGender());
	        c.setMobileNo(dto.getMobileNo());
	        c.setEmailId(dto.getEmailId());
	        
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