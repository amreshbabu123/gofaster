package com.quickmove.GoFaster.service;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.exception.DriverMobileNoNotFound;
import com.quickmove.GoFaster.exception.DriverNotFoundException;
import com.quickmove.GoFaster.repository.BookingRepository;
import com.quickmove.GoFaster.repository.CustomerRepository;
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
    
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<ResponseStructure<Driver>> deleteDriverByMobileNo(Long mobileNo) {

    	Driver driver = driverRepository
    	        .findByMobileNo(mobileNo)
    	        .orElseThrow(() -> new DriverMobileNoNotFound("Driver not found"));

        driverRepository.delete(driver);

        ResponseStructure<Driver> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Driver deleted successfully");
        response.setData(driver);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    

    public ResponseEntity<ResponseStructure<?>> updateCurrentLocation(CurrentLocationDTO locationDto) {
        Double lat = locationDto.getLatitude();
        Double lon = locationDto.getLongitude();
        if (lat == null || lon == null) throw new IllegalArgumentException("Latitude and longitude are required");

        String address = locationIQService.getCityFromCoordinates(lat, lon);
        if (address == null || address.isEmpty()) address = "Unknown Location";

        ResponseStructure<Object> response = new ResponseStructure<>();

        // Driver case
        if (locationDto.getDriverMobileNo() != null) {
            Driver driver = driverRepository.findByMobileNo(locationDto.getDriverMobileNo())
                    .orElseThrow(() -> new DriverMobileNoNotFound("Driver not found"));
            driver.setLatitude(lat);
            driver.setLongitude(lon);
            driver.setCurrentAddress(address);
            response.setData(driverRepository.save(driver));
            response.setMessage("Driver location updated successfully");
            response.setStatuscode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Customer case
        if (locationDto.getCustomerMobileNo() != null) {
            Customer customer = customerRepository.findByMobileNo(locationDto.getCustomerMobileNo())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            customer.setLatitude(lat);
            customer.setLongitude(lon);
            customer.setCurrentLocation(address);
            response.setData(customerRepository.save(customer));
            response.setMessage("Customer location updated successfully");
            response.setStatuscode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        throw new IllegalArgumentException("Either driverMobileNo or customerMobileNo must be provided");
    }



    
    

    public ResponseEntity<ResponseStructure<Driver>> findDriver(long mobileNo) {

    	Driver driver = driverRepository
    	        .findByMobileNo(mobileNo)
    	        .orElseThrow(() -> new DriverMobileNoNotFound("Driver not found"));

        ResponseStructure<Driver> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Driver with mobileNo " + mobileNo + " found successfully");
        rs.setData(driver);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    
    
    
    
    
    
    
    public ResponseEntity<ResponseStructure<BookingHistoryDto>> getDriverBookingHistoryByMobile(Long mobileNo) {

        List<Booking> bookings = bookingRepository.findByDriver_MobileNo(mobileNo);

        if (bookings == null || bookings.isEmpty()) {
            throw new RuntimeException("No booking history found for this driver");
        }

        List<RideDetailsDto> history = new ArrayList<>();
        double totalAmount = 0;

        for (Booking booking : bookings) {

            // Only COMPLETED and CANCELLED
            if (!"COMPLETED".equalsIgnoreCase(booking.getBookingStatus()) &&
                !"CANCELLED".equalsIgnoreCase(booking.getBookingStatus())) {
                continue;
            }

            RideDetailsDto ride = new RideDetailsDto();
            ride.setFromLocation(booking.getSourceLocation());
            ride.setToLocation(booking.getDestinationLocation());
            ride.setDistance(booking.getDistanceTravelled());
            ride.setFare(booking.getFare());
            ride.setStatus(booking.getBookingStatus());   // 🔥 IMPORTANT

            history.add(ride);

            // Only completed adds to earnings
            if ("COMPLETED".equalsIgnoreCase(booking.getBookingStatus())) {
                totalAmount += booking.getFare();
            }
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

        // 1️⃣ Fetch driver
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if (optionalDriver.isEmpty()) {
            structure.setStatuscode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("Driver not found");
            structure.setData(null);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }

        Driver driver = optionalDriver.get();

        // 2️⃣ Auto-unblock if needed
        autoUnblockIfNextDayPassed(driver);

        // 3️⃣ Block check
        if ("BLOCKED".equalsIgnoreCase(driver.getStatus())) {
            structure.setStatuscode(HttpStatus.BAD_REQUEST.value());
            structure.setMessage("Driver is blocked. Please try next day");
            structure.setData(driver);
            return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
        }

        // 4️⃣ Fetch booking
        Optional<Booking> optionalBooking = bookingRepository.findByIdAndDriverId(bookingId, driverId);
        if (optionalBooking.isEmpty()) {
            structure.setStatuscode(HttpStatus.NOT_FOUND.value());
            structure.setMessage("Booking not found for this driver");
            structure.setData(driver);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }

        Booking booking = optionalBooking.get();

        // ✅ Only cancel ACTIVE rides
        if (!"ACTIVE".equalsIgnoreCase(booking.getBookingStatus())) {
            structure.setStatuscode(HttpStatus.BAD_REQUEST.value());
            structure.setMessage("Only active rides can be cancelled");
            structure.setData(driver);
            return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
        }

        // 5️⃣ Count previous cancels
        List<Booking> bookingList = bookingRepository.findByDriverId(driverId);
        long cancelCount = bookingList.stream()
                .filter(b -> "CANCELLED".equalsIgnoreCase(b.getBookingStatus()))
                .count();

        // 6️⃣ Cancel booking
        booking.setBookingStatus("CANCELLED");

        // 7️⃣ Update driver + vehicle
        driver.setStatus("Available");
        if (driver.getVehicle() != null) {
            driver.getVehicle().setVehicleavailabilityStatus("Available");
        }

        long totalCancels = cancelCount + 1;

        // 8️⃣ Block if >= 5 cancels
        if (totalCancels >= 5) {
            driver.setStatus("BLOCKED");
            driver.setBlockedAt(LocalDateTime.now());
            if (driver.getVehicle() != null) {
                driver.getVehicle().setVehicleavailabilityStatus("UNAVAILABLE");
            }
        }

        // 9️⃣ Save
        bookingRepository.save(booking);
        driverRepository.save(driver);

        // 🔟 Response
        structure.setStatuscode(HttpStatus.OK.value());
        structure.setMessage(
                totalCancels >= 5
                        ? "Booking cancelled. Driver is blocked until next day"
                        : "Booking cancelled successfully"
        );
        structure.setData(driver);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

 

    
    
    /* ================= AUTO UNBLOCK NEXT DAY ================= */
    private void autoUnblockIfNextDayPassed(Driver driver) {
        if (driver.getBlockedAt() != null) {
            // Compare dates only, ignore time
            if (driver.getBlockedAt().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
                driver.setStatus("Available");
                driver.setBlockedAt(null);
                if (driver.getVehicle() != null) {
                    driver.getVehicle().setVehicleavailabilityStatus("Available");
                }
                driverRepository.save(driver);
            }
        }
    }
}
  


