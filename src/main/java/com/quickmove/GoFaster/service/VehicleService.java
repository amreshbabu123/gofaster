package com.quickmove.GoFaster.service;

import com.quickmove.GoFaster.dto.*;
import com.quickmove.GoFaster.entity.Customer;
import com.quickmove.GoFaster.entity.Vehicle;
import com.quickmove.GoFaster.repository.CustomerRepository;
import com.quickmove.GoFaster.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private LocationIQService locationIQ;

    public AvailableVehicleDto seeAllAvailabilityVehicle(long mobileNo, String destinationCity) {

        // ------------------------ 1. Fetch Customer ------------------------
        Customer customer = customerRepo.findByMobileNo(mobileNo);
        if (customer == null) {
            throw new RuntimeException("Customer NOT found with mobileNo: " + mobileNo);
        }

        String sourceCity = customer.getCurrentLocation();

        // ------------------------ 2. Calculate Distance ------------------------
        double distance = locationIQ.getDistance(sourceCity, destinationCity);

        // ------------------------ 3. Fetch Available Vehicles ------------------------
        List<Vehicle> vehicleList = vehicleRepo.findByVehiclecurrentCity(sourceCity);

        // ------------------------ 4. Prepare Response DTO ------------------------
        AvailableVehicleDto result = new AvailableVehicleDto();
        result.setSource(sourceCity);
        result.setDestination(destinationCity);
        result.setDistance(distance);

        // ---------- Customer DTO ----------
        CustomerDto cd = new CustomerDto();
        cd.setName(customer.getName());
        cd.setAge(customer.getAge());
        cd.setGender(customer.getGender());
        cd.setMobileNo(customer.getMobileNo());
        cd.setEmailId(customer.getEmailId());
        result.setCustomer(cd);

        // ---------- Vehicle Details ----------
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

            double fare = v.getPricePerKm() * distance;
            double time = distance / v.getAverageSpeed(); // HOURS

            VehicleDetails vd = new VehicleDetails();
            vd.setVehicle1(vdto);
            vd.setFare(fare);
            vd.setEstimatedTime(time);

            detailsList.add(vd);
        }

        result.setAvailableVehicles(detailsList);

        return result;
    }
}
