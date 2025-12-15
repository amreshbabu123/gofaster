package com.quickmove.GoFaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.quickmove.GoFaster.util.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleCustomerNotFound(CustomerNotFoundException custnot) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Customer is not Found");
        response.setData(custnot.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleDriverNotFound(DriverNotFoundException drivernot) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Driver is not Found");
        response.setData(drivernot.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DriverMobileNoNotFound.class)
    public ResponseEntity<ResponseStructure<String>> handleDriverMobileException(DriverMobileNoNotFound drivernot) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Driver Mobile Number Not Found");
        response.setData(drivernot.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleBookingNotFound(BookingNotFoundException ex) {
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Booking id is not found");
        response.setData(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
