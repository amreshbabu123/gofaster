package com.quickmove.GoFaster.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickmove.GoFaster.dto.UPIPaymentDto;
import com.quickmove.GoFaster.entity.Booking;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Payments;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.BookingRepository;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.PaymentsRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<ResponseStructure<Payments>> driverCompleteRidePayByCash(Long bookingId) {

        // Fetch booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update booking status
        booking.setBookingStatus("COMPLETED");
        booking.setPaymentStatus("PAID");

        // Fetch customer and update
        Customer customer = booking.getCustomer();
        if (customer != null) {
            customer.setActiveBookingFlag(false);
            customerRepository.save(customer);
        }

        // Fetch vehicle and update availability
        Vehicle vehicle = booking.getVehicle();
        if (vehicle != null) {
            vehicle.setVehicleavailabilityStatus("Available");
            vehicleRepository.save(vehicle);
        } else {
            throw new RuntimeException("Vehicle not assigned to this booking");
        }

        // Create payment record
        Payments payment = new Payments();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setVehicle(vehicle);
        payment.setAmount(booking.getFare());
        payment.setPaymentMode("CASH");
        payment.setPaymentStatus("PAID");

        // Save booking and payment
        bookingRepository.save(booking);
        paymentsRepository.save(payment);

        // Prepare response
        ResponseStructure<Payments> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Ride completed & payment done by cash");
        response.setData(payment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    

    public ResponseEntity<ResponseStructure<UPIPaymentDto>> driverCompleteRidePayByUPI(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        double fare = booking.getFare();
        String upiId = booking.getDriver().getUpiId();

        // QR API URL
        String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data="
                + "upi://pay?pa=" + upiId + "&am=" + fare;

        // Call API
        byte[] qrImage = restTemplate.getForObject(qrUrl, byte[].class);

        // DTO
        UPIPaymentDto dto = new UPIPaymentDto();
        dto.setFare(fare);
        dto.setQr(qrImage);

        ResponseStructure<UPIPaymentDto> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("UPI QR generated successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }
    

    
    public ResponseEntity<ResponseStructure<Payments>> upiPaymentConfirmed(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update booking
        booking.setBookingStatus("COMPLETED");
        booking.setPaymentStatus("PAID");

        // Vehicle update
        Vehicle vehicle = booking.getVehicle();
        vehicle.setVehicleavailabilityStatus("Available");

        // Customer update (if flag exists)
        Customer customer = booking.getCustomer();
        // customer.setActiveBookingFlag(false); // enable if field exists

        // Create payment
        Payments payment = new Payments();
        payment.setBooking(booking);
        payment.setCustomer(customer);
        payment.setVehicle(vehicle);
        payment.setAmount(booking.getFare());
        payment.setPaymentMode("UPI");
        payment.setPaymentStatus("PAID");

        // Save all
        paymentsRepository.save(payment);
        bookingRepository.save(booking);
        vehicleRepository.save(vehicle);
        customerRepository.save(customer);

        ResponseStructure<Payments> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("UPI payment confirmed and ride completed");
        response.setData(payment);

        return ResponseEntity.ok(response);
    }
}
