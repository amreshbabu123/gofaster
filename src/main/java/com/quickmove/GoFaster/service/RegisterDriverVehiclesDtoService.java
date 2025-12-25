package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Userr;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.repository.UserRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

import jakarta.transaction.Transactional;

@Service
public class RegisterDriverVehiclesDtoService {

	    @Autowired
	    private DriverRepository driverRepo;

	    @Autowired
	    private VehicleRepository vehicleRepo;
	    
	    @Autowired
	    private UserRepository userRepo;
	    @Autowired
	     private PasswordEncoder passwordEncoder;

        @Transactional
	    public ResponseEntity<ResponseStructure<Driver>> registerDriver(RegisterDriverVehiclesDto dto) {

	        // ❌ Check duplicate mobile
	        if(userRepo.findByMobileno(dto.getMobileNumber()).isPresent()) {
	            throw new RuntimeException("Mobile number already exists!");
	        }

	        // 1️⃣ Create User for driver
	        Userr user = new Userr();
	        user.setMobileno(dto.getMobileNumber());
	        user.setPassword(passwordEncoder.encode(dto.getPassword()));
	        user.setRole("DRIVER");
	        userRepo.save(user);

	        // 2️⃣ Create Vehicle
	        Vehicle vehicle = new Vehicle();
	        vehicle.setVehicleName(dto.getVehicleName());
	        vehicle.setVehicleNo(dto.getVehicleNo());
	        vehicle.setVehicleType(dto.getVehicleType());
	        vehicle.setVehiclecapaCity(dto.getVehicleCapacity());
	        vehicle.setPricePerKm(dto.getPricePerKm());
	        vehicleRepo.save(vehicle);

	        // 3️⃣ Create Driver
	        Driver driver = new Driver();
	        driver.setName(dto.getDriverName());
	        driver.setAge(dto.getAge());
	        driver.setGender(dto.getGender());
	        driver.setMobileNo(dto.getMobileNumber());
	        driver.setMailId(dto.getMailId());
	        driver.setLicenceNo(dto.getLicenceNo());
	        driver.setUpiId(dto.getUpiId());
	        driver.setLatitude(dto.getLatitude());
	        driver.setLongitude(dto.getLongitude());
	        driver.setUser(user);
	        driver.setVehicle(vehicle);

	        Driver savedDriver = driverRepo.save(driver);

	        // 4️⃣ Response
	        ResponseStructure<Driver> response = new ResponseStructure<>();
	        response.setStatuscode(HttpStatus.CREATED.value());
	        response.setMessage("Driver and vehicle registered successfully");
	        response.setData(savedDriver);

	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

}
