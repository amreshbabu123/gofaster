package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;

@Service
public class RegisterDriverVehiclesDtoService {

	    @Autowired
	    private DriverRepository driverRepo;

	    @Autowired
	    private VehicleRepository vehicleRepo;

	    public Driver saveRegisterDriverVehiclesDto(RegisterDriverVehiclesDto registerDriverVehicleDto) {

	        Vehicle vehicle = new Vehicle();
	        vehicle.setVehicleName(registerDriverVehicleDto.getVehicleName());
	        vehicle.setVehicleNo(String.valueOf(registerDriverVehicleDto.getVehicleNo()));
	        vehicle.setVehicleType(registerDriverVehicleDto.getVehicleType());
	        vehicle.setVehiclecapaCity(registerDriverVehicleDto.getVehicleCapacity());
	        vehicle.setPricePerKm(registerDriverVehicleDto.getPricePerKm());

	        vehicleRepo.save(vehicle);

	        Driver driver = new Driver();
	        driver.setLicenceNo(String.valueOf(registerDriverVehicleDto.getLicenceNo()));
	        driver.setUpiId(String.valueOf(registerDriverVehicleDto.getUpiId()));
	        driver.setName(registerDriverVehicleDto.getDriverName());
	        driver.setAge(registerDriverVehicleDto.getAge());
	        driver.setMobileNo(registerDriverVehicleDto.getMobileNumber());
	        driver.setGender(String.valueOf(registerDriverVehicleDto.getGender()));
	        driver.setMailId(registerDriverVehicleDto.getMailId());
	        driver.setVehicle(vehicle);

	        return driverRepo.save(driver);
	    }
}
