package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.service.RegisterDriverVehiclesDtoService;

@RestController
public class RegisterDriverVehiclesDtoController {

	@Autowired 
	private RegisterDriverVehiclesDtoService ds;
	
	@PostMapping("/registerdrivervehiclesdto")
	public Driver saveRegisterDriverVehicle(@RequestBody RegisterDriverVehiclesDto dv) { 
		return ds.saveRegisterDriverVehiclesDto(dv);
		}
	
	

}
