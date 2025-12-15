package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickmove.GoFaster.dto.UPIPaymentDto;
import com.quickmove.GoFaster.entity.Payments;
import com.quickmove.GoFaster.service.PaymentsService;
import com.quickmove.GoFaster.util.ResponseStructure;

@RestController
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/driver/completeride/paybycash")
    public ResponseEntity<ResponseStructure<Payments>> driverCompleteRidePayByCash(@RequestParam Long bookingId) {
        return paymentsService.driverCompleteRidePayByCash(bookingId);
    }
    
    @PostMapping("/driver/completeride/generatedUPI")
    public ResponseEntity<ResponseStructure<UPIPaymentDto>> driverCompleteRidePaybyUPI(@RequestParam Long bookingId) {
        return paymentsService.driverCompleteRidePayByUPI(bookingId);
    }
    
    @PostMapping("/upiPaymentConfirmed")
    public ResponseEntity<ResponseStructure<Payments>> upiPaymentConfirmed(@RequestParam Long bookingId) {
        return paymentsService.upiPaymentConfirmed(bookingId);
    }

}
