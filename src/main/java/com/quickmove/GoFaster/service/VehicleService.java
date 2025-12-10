package com.quickmove.GoFaster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickmove.GoFaster.dto.*;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;

@Service
public class VehicleService {

    @Autowired 
    private VehicleRepository vehicleRepo;

    @Autowired 
    private CustomerRepository customerRepo;

    @Autowired 
    private LocationIQService locationIQ;

    @Autowired 
    private ORSService orsService;

    public AvailableVehicleDto seeAllAvailabilityVehicle(long mobileNo, String destinationCity) {

        // 1. Fetch customer
        Customer customer = customerRepo.findByMobileNo(mobileNo);
        if (customer == null) throw new RuntimeException("Customer NOT found.");

        // 2. Get source city or fallback to coordinates
        String sourceCity = customer.getCurrentLocation();
        if (sourceCity == null || sourceCity.isEmpty()) {
            // fallback: use coordinates as "lat,lon" string
            sourceCity = customer.getLatitude() + "," + customer.getLongitude();
        }

        // 3. Get coordinates
        double[] src = locationIQ.getCoordinates(sourceCity);
        double[] dst = locationIQ.getCoordinates(destinationCity);

        // 4. Calculate distance and estimated time using ORS
        ORSDistanceResponse ors = orsService.getDistance(src[0], src[1], dst[0], dst[1]);

        // 5. Fetch vehicles available in source city
        List<Vehicle> vehicleList = vehicleRepo.findByVehiclecurrentCityIgnoreCase(sourceCity);
        if (vehicleList == null || vehicleList.isEmpty()) {
            System.out.println("No vehicles found in " + sourceCity + ", fetching all available vehicles");
            vehicleList = vehicleRepo.findByVehicleavailabilityStatus("Available");
        }


        // 6. Prepare result DTO
        AvailableVehicleDto result = new AvailableVehicleDto();
        result.setSource(sourceCity);
        result.setDestination(destinationCity);
        result.setDistance(Math.round(ors.getDistanceKm() * 100.0) / 100.0);

        // 7. Map customer to DTO
        CustomerDto cd = new CustomerDto();
        cd.setName(customer.getName());
        cd.setAge(customer.getAge());
        cd.setGender(customer.getGender());
        cd.setMobileNo(customer.getMobileNo());
        cd.setEmailId(customer.getEmailId());
        cd.setLatitude(customer.getLatitude());
        cd.setLongitude(customer.getLongitude());
        result.setCustomer(cd);

        // 8. Map vehicles to DTO with fare and estimated time
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

            // Calculate fare
            double fare = Math.round(v.getPricePerKm() * ors.getDistanceKm() * 100.0) / 100.0;

            VehicleDetails vd = new VehicleDetails();
            vd.setVehicle1(vdto);
            vd.setFare(fare);
            vd.setEstimatedTime(Math.round(ors.getTimeHours() * 100.0) / 100.0);

            detailsList.add(vd);
        }

        result.setAvailableVehicles(detailsList);

        return result;
    }
}
