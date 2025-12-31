package com.quickmove.GoFaster.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quickmove.GoFaster.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // ✅ ONLY AVAILABLE vehicles in city
    List<Vehicle> findByVehiclecurrentCityIgnoreCaseAndVehicleavailabilityStatus(
            String city,
            String status
    );

    // fallback
    List<Vehicle> findByVehicleavailabilityStatus(String status);
}
