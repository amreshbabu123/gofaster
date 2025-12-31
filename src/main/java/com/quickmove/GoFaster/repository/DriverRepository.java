package com.quickmove.GoFaster.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import com.quickmove.GoFaster.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByMobileNo(Long mobileNo);
}


