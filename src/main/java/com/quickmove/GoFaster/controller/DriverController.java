package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.BookingHistoryDto;
import com.quickmove.GoFaster.dto.CurrentLocationDTO;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.service.DriverService;
import com.quickmove.GoFaster.util.ResponseStructure;
@RestController
public class DriverController {
	    @Autowired
	    private DriverService driverService;

	
	    @DeleteMapping("/deletedriverwithmobileNo")
	    public ResponseEntity<ResponseStructure<Driver>> deleteDriverByMobileNo(@RequestParam Long mobileNo) {
	    	return driverService.deleteDriverByMobileNo(mobileNo);
	    }

	    @PutMapping("/updatecurrentlocation")
	    public ResponseEntity<ResponseStructure<?>> updateLocation(@RequestBody CurrentLocationDTO locationDto) {
	        return driverService.updateCurrentLocation(locationDto);
	    }

	    
	    @GetMapping("/finddriverwithmobileno")
	    public  ResponseEntity<ResponseStructure<Driver>> findDriver(@RequestParam long mobileNo){
	    	return driverService.findDriver(mobileNo);	
	    }

	    @GetMapping("/driver/seeallbookinghistorywithmobileno")
	    public ResponseEntity<ResponseStructure<BookingHistoryDto>> getDriverBookingHistory(@RequestParam Long mobileNo) {
            return driverService.getDriverBookingHistoryByMobile(mobileNo);
	    }
	    
	    @PostMapping("/driver/cancelbooking")
	    public ResponseEntity<ResponseStructure<Driver>> driverCancelTheBooking(@RequestParam long driverId,@RequestParam long bookingId) {
           return driverService.driverCancelTheBooking(driverId, bookingId);
	    }
	     
}

