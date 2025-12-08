package com.quickmove.GoFaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.quickmove.GoFaster.util.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(DriverMobileNoNotFound.class)
	    public ResponseEntity<ResponseStructure<String>> handleDriverMobileNoNotFound(DriverMobileNoNotFound dr) {
		 ResponseStructure<String> stru = new ResponseStructure<>();
	        stru.setStatuscode(HttpStatus.NOT_FOUND.value());
	        stru.setMessage("Driver not found");
	        stru.setData(dr.getMessage());

	        return new ResponseEntity<>(stru, HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(DriverNotFoundException.class)
	    public ResponseStructure<Object> handleDriverException(DriverNotFoundException ex) {
	        return new ResponseStructure<>(404, ex.getMessage(), null);
	    }
	 
	 @ExceptionHandler(CustomerNotFoundException.class)
	    public ResponseStructure<Object> handleCustomerException(CustomerNotFoundException ex) {
	        return new ResponseStructure<>(404, ex.getMessage(), null);
	    }

}
