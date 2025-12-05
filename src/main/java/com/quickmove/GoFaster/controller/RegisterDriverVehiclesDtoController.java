package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.ResponseStructure;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.service.RegisterDriverVehiclesDtoService;

@RestController
public class RegisterDriverVehiclesDtoController {
	@Autowired
	private  RegisterDriverVehiclesDtoService ds;
	@PostMapping("/registerdrivervehiclesdto")
	public String saveRegisterDriverVehicle(@RequestBody RegisterDriverVehiclesDto dv) {
		
		return ds.saveRegisterDriverVehiclesDto(dv);
		
	}
	
	
	@GetMapping("/driver/{mobileNo}")
	public ResponseStructure<Driver> findDriver(@PathVariable long mobileNo){
		return ds.findDriver(mobileNo);
	}

	
	
	
	

}
