package com.quickmove.GoFaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickmove.GoFaster.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.customer.mobileNo = :mobileNo AND b.driver.status = 'booked'")
	Booking findActiveBooking(long mobileNo);
	
	@Query("SELECT b FROM Booking b WHERE b.driver.mobileNo = :mobileNo AND b.driver.status = 'booked'")
	Booking findDriverActiveBooking(long mobileNo);



}
