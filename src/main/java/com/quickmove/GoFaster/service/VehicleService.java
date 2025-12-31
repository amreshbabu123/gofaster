package com.quickmove.GoFaster.service;

import java.util.ArrayList; 
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.*;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.exception.CustomerNotFoundException;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;
import com.quickmove.GoFaster.util.ResponseStructure;

@Service
public class VehicleService {
	
	 private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    @Autowired 
    private VehicleRepository vehicleRepo;

    @Autowired 
    private CustomerRepository customerRepo;

    @Autowired 
    private LocationIQService locationIQ;

    @Autowired 
    private ORSService orsService;

    public ResponseEntity<ResponseStructure<AvailableVehicleDto>>seeAllAvailabilityVehicle(long mobileNo, String destinationCity) {

        // 1️⃣ Fetch customer
        Customer customer = customerRepo
                .findByMobileNo(mobileNo)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // 2️⃣ Source city
        String sourceCity = customer.getCurrentLocation();
        if (sourceCity == null || sourceCity.isEmpty()) {
            sourceCity = customer.getLatitude() + "," + customer.getLongitude();
        }

        // 3️⃣ Coordinates
        double[] src = locationIQ.getCoordinates(sourceCity);
        double[] dst = locationIQ.getCoordinates(destinationCity);

        ORSDistanceResponse ors =
                orsService.getDistance(src[0], src[1], dst[0], dst[1]);

        // 4️⃣ 🔥 FETCH ONLY AVAILABLE VEHICLES 🔥
        List<Vehicle> vehicleList =
                vehicleRepo.findByVehiclecurrentCityIgnoreCaseAndVehicleavailabilityStatus(
                        sourceCity,
                        "Available"
                );

        // fallback if none in city
        if (vehicleList.isEmpty()) {
            vehicleList = vehicleRepo.findByVehicleavailabilityStatus("Available");
        }

        // 5️⃣ Prepare DTO
        AvailableVehicleDto result = new AvailableVehicleDto();
        result.setSource(sourceCity);
        result.setDestination(destinationCity);
        result.setDistance(Math.round(ors.getDistanceKm() * 100.0) / 100.0);

        // customer DTO
        CustomerDto cd = new CustomerDto();
        cd.setName(customer.getName());
        cd.setMobileNo(customer.getMobileNo());
        cd.setLatitude(customer.getLatitude());
        cd.setLongitude(customer.getLongitude());
        result.setCustomer(cd);

        // 6️⃣ Vehicle mapping
        List<VehicleDetails> detailsList = new ArrayList<>();

        for (Vehicle v : vehicleList) {

            // 🔒 DOUBLE SAFETY CHECK
            if (!"Available".equalsIgnoreCase(v.getVehicleavailabilityStatus())) {
                continue;
            }

            VehicleDto vdto = new VehicleDto();
            vdto.setVehicleName(v.getVehicleName());
            vdto.setVehicleNo(v.getVehicleNo());
            vdto.setVehicleType(v.getVehicleType());
            vdto.setVehiclecapaCity(v.getVehiclecapaCity());
            vdto.setPricePerKm(v.getPricePerKm());

            double fare =
                    Math.round(v.getPricePerKm() * ors.getDistanceKm() * 100.0) / 100.0;

            VehicleDetails vd = new VehicleDetails();
            vd.setVehicle(vdto);
            vd.setFare(fare);
            vd.setEstimatedTime(
                    Math.round(ors.getTimeHours() * 100.0) / 100.0
            );
            vd.setDriverMobileNo(v.getDriver().getMobileNo());

            detailsList.add(vd);
        }

        result.setAvailableVehicles(detailsList);

        ResponseStructure<AvailableVehicleDto> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Available vehicles fetched successfully");
        response.setData(result);

        return ResponseEntity.ok(response);
    }

}