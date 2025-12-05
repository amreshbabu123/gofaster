package com.quickmove.GoFaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickmove.GoFaster.entity.Driver;
@Repository
public interface DriverRepository  extends JpaRepository<Driver, Long>  {

	Driver findByMobileNo(long mobileNo);

}
