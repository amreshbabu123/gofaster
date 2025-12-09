package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.CurrentLocationDTO;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private LocationIQService locationIQService;

    public Driver deleteDriverByMobileNo(Long mobileNo) {
        Driver driver = driverRepository.findByMobileNo(mobileNo);
        if(driver == null) {
            throw new DriverMobileNoNotFound();
        }
        driverRepository.delete(driver);
        return driver;
    }
    
    public Driver updateCurrentVehicleLocation(Long mobileNo, CurrentLocationDTO locationDto) {

        Driver driver = driverRepository.findByMobileNo(mobileNo);

        if (driver == null) {
            throw new RuntimeException("Driver Not Found with mobile: " + mobileNo);
        }

<<<<<<< HEAD
        driver.setLatitude(dto.getLatitude());
        driver.setLongitude(dto.getLongitude());

        // FIX: method now exists again
        String address = locationIQService.getAddressFromCoordinates(dto.getLatitude(), dto.getLongitude());
=======
        
        driver.setLatitude(locationDto.getLatitude());
        driver.setLongitude(locationDto.getLongitude());

       
        String address = locationIQService.getAddressFromCoordinates(locationDto.getLatitude(), locationDto.getLongitude());
>>>>>>> c5d25d84610952de76282d80bf3c9e20fbef9dcf
        driver.setCurrentAddress(address);

        return driverRepository.save(driver);
    }


	public ResponseStructure<Driver> findDriver(long mobileNo) {
		 Driver driver = driverRepository.findByMobileNo(mobileNo);
		 if(driver == null) {
	            throw new DriverMobileNoNotFound();
	        }
		
		 ResponseStructure<Driver> rs =new ResponseStructure<Driver>();
			
			rs.setStatuscode(HttpStatus.FOUND.value());
			rs.setMessage("Driver with monileNo " +mobileNo + "found succesfully");
			rs.setData(driver);
			return rs;
	}

	
}

