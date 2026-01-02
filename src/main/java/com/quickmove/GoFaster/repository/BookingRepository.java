package com.quickmove.GoFaster.repository;

import java.util.List;   
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("""
		    SELECT b FROM Booking b
		    WHERE b.customer.mobileNo = :mobileNo
		    AND b.bookingStatus IN ('ACTIVE', 'ONGOING', 'COMPLETED')
		    ORDER BY b.id DESC
		""")
		List<Booking> findActiveBookings(@Param("mobileNo") long mobileNo);


	 // ✅ Driver active booking (ONLY ONE)
	@Query("SELECT b FROM Booking b WHERE b.driver.mobileNo = :mobileNo AND b.bookingStatus IN ('ACTIVE','ONGOING')")
	Optional<Booking> findDriverActiveBooking(@Param("mobileNo") long mobileNo);

    
	List<Booking> findByDriver_MobileNoAndBookingStatus(Long mobileNo, String bookingStatus);
	
	
	 Optional<Booking> findByIdAndDriverId(long bookingId, long driverId);
	 

	 List<Booking> findByDriverId(long driverId);
	 
	 @Query("""
			  SELECT b FROM Booking b
			  WHERE b.driver.mobileNo = :mobileNo
			  AND b.bookingStatus = 'COMPLETED'
			  AND b.paymentStatus = 'NOT_PAID'
			  ORDER BY b.id DESC
			""")
			Optional<Booking> findCompletedUnpaidBooking(long mobileNo);


	

}



