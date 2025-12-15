package com.quickmove.GoFaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quickmove.GoFaster.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.customer.mobileNo = :mobileNo AND b.bookingStatus = 'ACTIVE'")
    Booking findActiveBooking(@Param("mobileNo") long mobileNo);

    @Query("SELECT b FROM Booking b WHERE b.driver.mobileNo = :mobileNo AND b.bookingStatus = 'ACTIVE'")
    Booking findDriverActiveBooking(@Param("mobileNo") long mobileNo);

	List<Booking> findByDriver_MobileNoAndBookingStatus(Long mobileNo, String bookingStatus);
}



