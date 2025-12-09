package com.quickmove.GoFaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickmove.GoFaster.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByMobileNo(long mobileNo);

}
