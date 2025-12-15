package com.quickmove.GoFaster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.BookingHistoryDto;
import com.quickmove.GoFaster.dto.CurrentLocationDTO;
import com.quickmove.GoFaster.dto.RideDetailsDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
import com.quickmove.GoFaster.repository.BookingRepository;
import com.quickmove.GoFaster.repository.DriverRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private LocationIQService locationIQService;
    
    @Autowired
    private BookingRepository bookingRepository;

    public ResponseEntity<ResponseStructure<Driver>> deleteDriverByMobileNo(Long mobileNo) {

        Driver driver = driverRepository.findByMobileNo(mobileNo);

        if (driver == null) {
            throw new DriverMobileNoNotFound("Driver not found with mobile: " + mobileNo);
        }

        driverRepository.delete(driver);

        ResponseStructure<Driver> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Driver deleted successfully");
        response.setData(driver);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    

    
    public ResponseEntity<ResponseStructure<Driver>> updateCurrentVehicleLocation(
            Long mobileNo, CurrentLocationDTO locationDto) {

        Driver driver = driverRepository.findByMobileNo(mobileNo);

        if (driver == null) {
            throw new DriverMobileNoNotFound(
                    "Driver not found with mobile: " + mobileNo);
        }

        double lat = locationDto.getLatitude();
        double lon = locationDto.getLongitude();

        // Update coordinates
        driver.setLatitude(lat);
        driver.setLongitude(lon);

        // Get human-readable address
        String address = locationIQService.getAddressFromCoordinates(lat, lon);
        driver.setCurrentAddress(address);

        Driver updatedDriver = driverRepository.save(driver);

        ResponseStructure<Driver> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Driver location updated successfully");
        response.setData(updatedDriver);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    
    

    public ResponseEntity<ResponseStructure<Driver>> findDriver(long mobileNo) {

        Driver driver = driverRepository.findByMobileNo(mobileNo);

        if (driver == null) {
            throw new DriverMobileNoNotFound(
                    "Driver not found with mobile number: " + mobileNo);
        }

        ResponseStructure<Driver> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Driver with mobileNo " + mobileNo + " found successfully");
        rs.setData(driver);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    
    
    
    
    
    
    public ResponseEntity<ResponseStructure<BookingHistoryDto>>getDriverBookingHistoryByMobile(Long mobileNo) {

        List<Booking> bookings =
                bookingRepository.findByDriver_MobileNoAndBookingStatus(
                        mobileNo, "COMPLETED");

        if (bookings.isEmpty()) {
            throw new RuntimeException("No booking history found for this driver");
        }

        List<RideDetailsDto> history = new ArrayList<>();
        double totalAmount = 0;

        for (Booking booking : bookings) {

            RideDetailsDto ride = new RideDetailsDto();
            ride.setFromLocation(booking.getSourceLocation());
            ride.setToLocation(booking.getDestinationLocation());
            ride.setDistance(booking.getDistanceTravelled());
            ride.setFare(booking.getFare());

            history.add(ride);
            totalAmount += booking.getFare();
        }

        BookingHistoryDto dto = new BookingHistoryDto();
        dto.setHistory(history);
        dto.setToatalAmount(totalAmount);

        ResponseStructure<BookingHistoryDto> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Driver booking history fetched successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }
	
}

