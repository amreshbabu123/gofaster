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

    public ResponseEntity<ResponseStructure<AvailableVehicleDto>> seeAllAvailabilityVehicle(long mobileNo, String destinationCity) {

        
        Customer customer = customerRepo.findByMobileNo(mobileNo);
        if (customer == null) throw new RuntimeException("Customer NOT found.");

      
        String sourceCity = customer.getCurrentLocation();
        if (sourceCity == null || sourceCity.isEmpty()) {
            sourceCity = customer.getLatitude() + "," + customer.getLongitude();
        }

     
        double[] src = locationIQ.getCoordinates(sourceCity);
        double[] dst = locationIQ.getCoordinates(destinationCity);
        if (src == null || dst == null) {
            throw new RuntimeException("Invalid source or destination coordinates");
        }

      
        ORSDistanceResponse ors = orsService.getDistance(src[0], src[1], dst[0], dst[1]);

     
        List<Vehicle> vehicleList = vehicleRepo.findByVehiclecurrentCityIgnoreCase(sourceCity);
        if (vehicleList == null || vehicleList.isEmpty()) {
            log.info("No vehicles found in {}, fetching all available vehicles", sourceCity);
            vehicleList = vehicleRepo.findByVehicleavailabilityStatus("Available");
        }

      
        AvailableVehicleDto result = new AvailableVehicleDto();
        result.setSource(sourceCity);
        result.setDestination(destinationCity);
        result.setDistance(Math.round(ors.getDistanceKm() * 100.0) / 100.0);

     
        CustomerDto cd = new CustomerDto();
        cd.setName(customer.getName());
        cd.setAge(customer.getAge());
        cd.setGender(customer.getGender());
        cd.setMobileNo(customer.getMobileNo());
        cd.setEmailId(customer.getEmailId());
        cd.setLatitude(customer.getLatitude());
        cd.setLongitude(customer.getLongitude());
        result.setCustomer(cd);

      
        List<VehicleDetails> detailsList = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            VehicleDto vdto = new VehicleDto();
            vdto.setVehicleName(v.getVehicleName());
            vdto.setVehicleNo(v.getVehicleNo());
            vdto.setVehicleType(v.getVehicleType());
            vdto.setVehicleModel(v.getVehicleModel());
            vdto.setVehiclecapaCity(v.getVehiclecapaCity());
            vdto.setPricePerKm(v.getPricePerKm());
            vdto.setAverageSpeed(v.getAverageSpeed());

            double fare = Math.round(v.getPricePerKm() * ors.getDistanceKm() * 100.0) / 100.0;

            VehicleDetails vd = new VehicleDetails();
            vd.setVehicle(vdto);
            vd.setFare(fare);
            vd.setEstimatedTime(Math.round(ors.getTimeHours() * 100.0) / 100.0);
            detailsList.add(vd);
        }

        result.setAvailableVehicles(detailsList);

    
        ResponseStructure<AvailableVehicleDto> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Available vehicles fetched successfully");
        response.setData(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
