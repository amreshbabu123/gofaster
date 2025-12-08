package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.BookVehicleDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.exception.DriverNotFoundException;
import com.quickmove.GoFaster.service.BookingService;
import com.quickmove.GoFaster.util.ResponseStructure;

@RestController
public class BookingController {
	 @Autowired 
	 private BookingService bookingService;
	 
	 @PostMapping("/bookVehicle")
	 public ResponseStructure<Booking> bookVehicle(@RequestBody BookVehicleDto bookVehicleDto) throws DriverNotFoundException, CustomerNotFoundException{
	     return bookingService.bookVehicle(bookVehicleDto);
	 }

	 


}
