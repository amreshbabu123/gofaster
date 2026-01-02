package com.quickmove.GoFaster.controller;

import org.springframework.web.bind.annotation.RestController;   

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quickmove.GoFaster.dto.BookVehicleDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.service.BookingService;
import com.quickmove.GoFaster.util.ResponseStructure;


@RestController
public class BookingController {
	 @Autowired 
	 private BookingService bookingService;
	 
	 
	 @PostMapping("/customer/bookVehicle")
	 public ResponseEntity<ResponseStructure<Booking>> bookVehicle(@RequestBody BookVehicleDto bookVehicleDto){
	     return bookingService.bookVehicle(bookVehicleDto);
	 }
	 
	 @GetMapping("/customer/activebooking")
	 public ResponseEntity<ResponseStructure<Booking>> customeractiveBooking(@RequestParam long mobileNo){
		 return bookingService.customeractiveBooking(mobileNo); 
	 }
	 
	 @GetMapping("/driver/activebooking")
	 public ResponseEntity<ResponseStructure<Booking>> driverActiveBooking(@RequestParam long mobileNo){
	     return bookingService.getDriverActiveBooking(mobileNo);
	 }
	 @PutMapping("/driver/acceptbooking")
	 public ResponseEntity<ResponseStructure<Booking>> acceptBooking(
	         @RequestParam long bookingId,
	         @RequestParam long driverMobileNo) {
	     return bookingService.acceptBooking(bookingId, driverMobileNo);
	 }

	 @PutMapping("/driver/complete")
	    public ResponseEntity<ResponseStructure<Booking>> completeRideByDriver(
	            @RequestParam long driverMobileNo,
	            @RequestParam long bookingId) {

	        return bookingService.completeRideByDriver(driverMobileNo, bookingId);
	    }
	 
	 @GetMapping("/driver/pending-payment")
	 public ResponseEntity<ResponseStructure<Booking>> pendingPayment(@RequestParam long mobileNo) {
		 return bookingService.getDriverPendingPayment(mobileNo);
		 
	 }
}
	 



