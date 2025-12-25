package com.quickmove.GoFaster.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.quickmove.GoFaster.dto.CustomerDto;
import com.quickmove.GoFaster.dto.RegisterDriverVehiclesDto;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Driver;
import com.quickmove.GoFaster.entity.Userr;
import com.quickmove.GoFaster.repository.UserRepository;
import com.quickmove.GoFaster.service.CustomerService;
import com.quickmove.GoFaster.service.RegisterDriverVehiclesDtoService;
import com.quickmove.GoFaster.util.ResponseStructure;
import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RegisterDriverVehiclesDtoService driverService;
    
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/registercustomer")
    public ResponseEntity<ResponseStructure<Customer>> registerCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.registerCustomer(customerDto);
    }

    @PostMapping("/registerdriver")
    public ResponseEntity<ResponseStructure<Driver>> registerDriver(@RequestBody RegisterDriverVehiclesDto dto) {
        return driverService.registerDriver(dto);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String mobile = String.valueOf(request.getMobileno());

        // 1. Authenticate
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(mobile, request.getPassword())
        );

        // 2. Load user from DB
        Userr user = userRepository
                .findByMobileno(request.getMobileno())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate JWT
        String token = jwtUtil.generateToken(mobile);

        // 4. Return token + role
        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", user.getRole()
        ));
    }

}
