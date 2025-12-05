package com.quickmove.GoFaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickmove.GoFaster.entity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
