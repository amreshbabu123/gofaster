package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Userr;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.repository.UserRepo;
import com.quickmove.GoFaster.repository.VehicleRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class RegisterDriverVehiclesDtoService {

	    @Autowired
	    private DriverRepository driverRepo;

	    @Autowired
	    private VehicleRepository vehicleRepo;
	    
	    @Autowired
	    private UserRepo userRepo;

	    public ResponseEntity<ResponseStructure<Driver>> saveRegisterDriverVehiclesDto(
	            RegisterDriverVehiclesDto registerDriverVehicleDto) {

	        // Create Vehicle
	        Vehicle vehicle = new Vehicle();
	        vehicle.setVehicleName(registerDriverVehicleDto.getVehicleName());
	        vehicle.setVehicleNo(String.valueOf(registerDriverVehicleDto.getVehicleNo()));
	        vehicle.setVehicleType(registerDriverVehicleDto.getVehicleType());
	        vehicle.setVehiclecapaCity(registerDriverVehicleDto.getVehicleCapacity());
	        vehicle.setPricePerKm(registerDriverVehicleDto.getPricePerKm());

	        vehicleRepo.save(vehicle);
	        
	        //save user
	        Userr user=new Userr();
	        user.setMobileno(registerDriverVehicleDto.getMobileNumber());
	        user.setRole("Driver");
	        user.setPassword(registerDriverVehicleDto.getPassword());
	        
	        userRepo.save(user);
	        

	        // Create Driver
	        Driver driver = new Driver();
	        driver.setLicenceNo(String.valueOf(registerDriverVehicleDto.getLicenceNo()));
	        driver.setUpiId(String.valueOf(registerDriverVehicleDto.getUpiId()));
	        driver.setName(registerDriverVehicleDto.getDriverName());
	        driver.setAge(registerDriverVehicleDto.getAge());
	        driver.setMobileNo(registerDriverVehicleDto.getMobileNumber());
	        driver.setGender(String.valueOf(registerDriverVehicleDto.getGender()));
	        driver.setMailId(registerDriverVehicleDto.getMailId());
	        driver.setLatitude(registerDriverVehicleDto.getLatitude());
	        driver.setLongitude(registerDriverVehicleDto.getLongitude());
	        driver.setVehicle(vehicle);
	        driver.setUser(user);

	        Driver savedDriver = driverRepo.save(driver);
	        
	        // Prepare response
	        ResponseStructure<Driver> response = new ResponseStructure<>();
	        response.setStatuscode(HttpStatus.CREATED.value());
	        response.setMessage("Driver and vehicle registered successfully");
	        response.setData(savedDriver);

	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

}
