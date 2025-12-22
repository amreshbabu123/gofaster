package com.quickmove.GoFaster.service;

import java.time.LocalDateTime;
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
import com.quickmove.GoFaster.exception.BookingNotFoundException;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
import com.quickmove.GoFaster.exception.DriverNotFoundException;
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





    public ResponseEntity<ResponseStructure<Driver>> driverCancelTheBooking(long driverId, long bookingId) {

        ResponseStructure<Driver> structure = new ResponseStructure<>();

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        // 🔓 Auto-unblock check FIRST
        autoUnblockIf24HoursPassed(driver);

        if ("BLOCKED".equalsIgnoreCase(driver.getStatus())) {
            throw new IllegalStateException("Driver is blocked. Please try after 24 hours");
        }

        Booking booking = bookingRepository.findByIdAndDriverId(bookingId, driverId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found for this driver"));

        List<Booking> bookingList = bookingRepository.findByDriverId(driverId);

        int cancelCount = 0;
        for (Booking b : bookingList) {
            if ("CANCELLED_BY_DRIVER".equalsIgnoreCase(b.getBookingStatus())) {
                cancelCount++;
            }
        }

        // cancel current booking9
        booking.setBookingStatus("CANCELLED_BY_DRIVER");
        
        driver.setStatus("Available");
        driver.getVehicle().setVehicleavailabilityStatus("Available");

        int totalCancels = cancelCount + 1;

        // 🚫 BLOCK AFTER 5 CANCELLATIONS
        if (totalCancels >= 5) {
            driver.setStatus("BLOCKED");
            driver.setBlockedAt(LocalDateTime.now());

            if (driver.getVehicle() != null) {
                driver.getVehicle().setVehicleavailabilityStatus("UNAVAILABLE");
            }
        }

        driverRepository.save(driver);
        bookingRepository.save(booking);

        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage(
                totalCancels >= 5
                ? "Booking cancelled. Driver is blocked for 24 hours"
                : "Booking cancelled successfully"
        );
        structure.setData(driver);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
    
    
    
    
    private void autoUnblockIf24HoursPassed(Driver driver) {

        if ("BLOCKED".equalsIgnoreCase(driver.getStatus())
                && driver.getBlockedAt() != null) {

            if (driver.getBlockedAt().plusHours(24).isBefore(LocalDateTime.now())) {

                driver.setStatus("Available");
                driver.setBlockedAt(null);

                if (driver.getVehicle() != null) {
                    driver.getVehicle().setVehicleavailabilityStatus("AVAILABLE");
                }

                driverRepository.save(driver);
            }
        }
    }


}

