package com.quickmove.GoFaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quickmove.GoFaster.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long>  {
	
    Driver findByMobileNo(Long mobileNo);
}

