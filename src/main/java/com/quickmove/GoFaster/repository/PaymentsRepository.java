package com.quickmove.GoFaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickmove.GoFaster.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long>{
	

}
