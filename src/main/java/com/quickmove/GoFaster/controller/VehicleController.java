package com.quickmove.GoFaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.quickmove.GoFaster.dto.AvailableVehicleDto;
import com.quickmove.GoFaster.dto.ORSDistanceResponse;
import com.quickmove.GoFaster.service.ORSService;
import com.quickmove.GoFaster.service.VehicleService;

@RestController
public class VehicleController {

   @Autowired
   private VehicleService vehicleService;

    @GetMapping("/seeallavailabilityvehicle")
    public AvailableVehicleDto seeAllAvailabilityVehicle(@RequestParam long mobileNo,@RequestParam String destinationCity) {
     return vehicleService.seeAllAvailabilityVehicle(mobileNo, destinationCity);
    }
    
        @Autowired
        private ORSService orsService;

        @GetMapping("/distance")
        public ORSDistanceResponse testDistance(
                @RequestParam double sLat,
                @RequestParam double sLon,
                @RequestParam double dLat,
                @RequestParam double dLon) {

            return orsService.getDistance(sLat, sLon, dLat, dLon);
        }
  }

