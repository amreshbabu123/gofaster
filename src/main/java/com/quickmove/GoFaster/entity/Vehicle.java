package com.quickmove.GoFaster.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String vehicleNo;
	    private String type;
	    private String model;
	    private int capacity;
	    private String currentCity;
	    private String availabilityStatus;
	    private double pricePerKm;
	    @OneToOne
	    @JoinColumn(name = "driver_id")   // correct FK name
	    @JsonBackReference
	    private Driver driver;


		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		public String getCurrentCity() {
			return currentCity;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}
		public String getAvailabilityStatus() {
			return availabilityStatus;
		}
		public void setAvailabilityStatus(String availabilityStatus) {
			this.availabilityStatus = availabilityStatus;
		}
		public double getPricePerKm() {
			return pricePerKm;
		}
		public void setPricePerKm(double pricePerKm) {
			this.pricePerKm = pricePerKm;
		}
		
		public Driver getDriver() {
			return driver;
		}
		public void setDriver(Driver driver) {
			this.driver = driver;
		}
		
		public Vehicle(String name, String vehicleNo, String type, String model, int capacity, String currentCity,
				String availabilityStatus, double pricePerKm, Driver driver) {
			super();
			this.name = name;
			this.vehicleNo = vehicleNo;
			this.type = type;
			this.model = model;
			this.capacity = capacity;
			this.currentCity = currentCity;
			this.availabilityStatus = availabilityStatus;
			this.pricePerKm = pricePerKm;
			this.driver = driver;
		}
		public Vehicle() {
			super();
		}
		@Override
		public String toString() {
			return "Vehicle [id=" + id + ", name=" + name + ", vehicleNo=" + vehicleNo + ", type=" + type + ", model="
					+ model + ", capacity=" + capacity + ", currentCity=" + currentCity + ", availabilityStatus="
					+ availabilityStatus + ", pricePerKm=" + pricePerKm + ", driver=" + driver + "]";
		}
		

}
