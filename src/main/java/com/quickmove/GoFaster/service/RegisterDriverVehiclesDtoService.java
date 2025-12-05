package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.ResponseStructure;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.repository.RegisterDriverVehiclesDtoRepo;
import com.quickmove.GoFaster.repository.VehicleRepository;

@Service
public class RegisterDriverVehiclesDtoService {
//	@Autowired
//	private RegisterDriverVehiclesDtoRepo dr;
//      public RegisterDriverVehiclesDto saveRegisterDriverVehiclesDto(RegisterDriverVehiclesDto dv) {
//      return dr.save(dv);
//	}
      
      @Autowired
      private DriverRepository driverRepo;

      @Autowired
      private VehicleRepository vehicleRepo;

      public String saveRegisterDriverVehiclesDto(RegisterDriverVehiclesDto dto) {

          // Create Driver entity
          Driver driver = new Driver();
          driver.setLicenceNo(String.valueOf(dto.getLicenceNo()));
          driver.setUpiId(String.valueOf(dto.getUpiId()));
          driver.setName(dto.getDriverName());
          driver.setAge(dto.getAge());
          driver.setMobileNo(String.valueOf(dto.getMobileNumber()));
          driver.setGender(String.valueOf(dto.getGender()));
          driver.setMailId(dto.getMailId());
          driver.setStatus("Available");

          // Create Vehicle entity
          Vehicle vehicle = new Vehicle();
          vehicle.setName(dto.getVehicleName());
          vehicle.setVehicleNo(String.valueOf(dto.getVehicleNo()));
          vehicle.setType(dto.getVehicleType());
          vehicle.setCapacity(Integer.parseInt(dto.getVehicleCapacity()));
          vehicle.setCurrentCity("Unknown"); // you can use latitude & longitude
          vehicle.setPricePerKm(Double.parseDouble(dto.getPricePerKm()));

          // Set relationship
          vehicle.setDriver(driver);  
          driver.setVehicle(vehicle);

          // Save driver first (vehicle is auto-saved because of cascade)
          driverRepo.save(driver);

          return "Driver and Vehicle Registered Successfully!";
      }

	public ResponseStructure<Driver> findDriver(long mobileNo) {
		 Driver driver = driverRepo.findByMobileNo(mobileNo);
		 ResponseStructure<Driver> rs =new ResponseStructure<Driver>();
			
			rs.setStatuscode(HttpStatus.FOUND.value());
			rs.setMessage("Driver with monileNo " +mobileNo + "foundr succesfully");
			rs.setData(driver);
			return rs;
			
		
	}

      

	

     
  }
