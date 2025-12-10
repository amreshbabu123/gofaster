package com.quickmove.GoFaster.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quickmove.GoFaster.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	List<Vehicle> findByVehicleavailabilityStatus(String string);

	List<Vehicle> findByVehiclecurrentCityIgnoreCase(String sourceCity);

	


}

