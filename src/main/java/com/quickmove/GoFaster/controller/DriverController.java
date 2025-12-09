package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.CurrentLocationDTO;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.service.DriverService;
import com.quickmove.GoFaster.util.ResponseStructure;
@RestController
public class DriverController {
	    @Autowired
	    private DriverService driverService;

	
	    @DeleteMapping("/deletedriverwithmobileNo")
	    public Driver deleteDriverByMobileNo(@RequestParam Long mobileNo) {
	    	return driverService.deleteDriverByMobileNo(mobileNo);
	    }

	    @PutMapping("/updatecurrentvechiclelocation/{mobileNo}")
	    public Driver updateLocation(@PathVariable Long mobileNo,@RequestBody CurrentLocationDTO locationDto) {
             return driverService.updateCurrentVehicleLocation(mobileNo, locationDto);
	        }
	    
	    @GetMapping("/finddriverwithmobileno")
	    public  ResponseStructure<Driver> findDriver(@RequestParam long mobileNo){
	    	return driverService.findDriver(mobileNo);	
	    }

	    
	   

  }

