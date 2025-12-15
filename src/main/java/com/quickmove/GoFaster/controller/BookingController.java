package com.quickmove.GoFaster.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
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
	 
	 @PostMapping("/bookVehicle")
	 public ResponseEntity<ResponseStructure<Booking>> bookVehicle(@RequestBody BookVehicleDto bookVehicleDto){
	     return bookingService.bookVehicle(bookVehicleDto);
	 }
	 
	 @GetMapping("/seeactivebookingforcustomer")
	 public ResponseEntity<ResponseStructure<Booking>> customeractiveBooking(@RequestParam long mobileNo){
		 return bookingService.customeractiveBooking(mobileNo);
		 
	 }
	 
	 @GetMapping("/driverActiveBooking")
	 public ResponseEntity<ResponseStructure<Booking>> driverAtiveBooking(@RequestParam long mobileNo){
		 return bookingService.driverActiveBooking(mobileNo);
		 
	 }
	 
	 
	 
}
	 



