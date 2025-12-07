package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.repository.CustomerRepository;

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
	        // optionally set currentLocation from lat/long -> pass as string
	        customerRepo.save(c);
	        return c;
	    }

		public Customer findByMobile(long mobileNo) {
			  return customerRepo.findByMobileNo(mobileNo);

		}

		public boolean deleteByMobile(long mobileNo) {
			 Customer c = customerRepo.findByMobileNo(mobileNo);
		        if (c == null) return false;
		        customerRepo.delete(c);
		        return true;

		}


}