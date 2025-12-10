package com.quickmove.GoFaster.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String vehicleName;
	    private String vehicleNo;
	    private String vehicleType;
	    private String vehicleModel;
	    private String vehiclecapaCity;
	    private String vehiclecurrentCity;
	    private String vehicleavailabilityStatus="Available";
	    private double pricePerKm;
	    private int averageSpeed=60;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getVehicleName() {
			return vehicleName;
		}
		public void setVehicleName(String vehicleName) {
			this.vehicleName = vehicleName;
		}
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public String getVehicleType() {
			return vehicleType;
		}
		public void setVehicleType(String vehicleType) {
			this.vehicleType = vehicleType;
		}
		public String getVehicleModel() {
			return vehicleModel;
		}
		public void setVehicleModel(String vehicleModel) {
			this.vehicleModel = vehicleModel;
		}
		public String getVehiclecapaCity() {
			return vehiclecapaCity;
		}
		public void setVehiclecapaCity(String vehiclecapaCity) {
			this.vehiclecapaCity = vehiclecapaCity;
		}
		public String getVehiclecurrentCity() {
			return vehiclecurrentCity;
		}
		public void setVehiclecurrentCity(String vehiclecurrentCity) {
			this.vehiclecurrentCity = vehiclecurrentCity;
		}
		public String getVehicleavailabilityStatus() {
			return vehicleavailabilityStatus;
		}
		public void setVehicleavailabilityStatus(String vehicleavailabilityStatus) {
			this.vehicleavailabilityStatus = vehicleavailabilityStatus;
		}
		public double getPricePerKm() {
			return pricePerKm;
		}
		public void setPricePerKm(double pricePerKm) {
			this.pricePerKm = pricePerKm;
		}
		public int getAverageSpeed() {
			return averageSpeed;
		}
		public void setAverageSpeed(int averageSpeed) {
			this.averageSpeed = averageSpeed;
		}
		public Vehicle(Long id, String vehicleName, String vehicleNo, String vehicleType, String vehicleModel,
				String vehiclecapaCity, String vehiclecurrentCity, String vehicleavailabilityStatus, double pricePerKm,
				int averageSpeed) {
			super();
			this.id = id;
			this.vehicleName = vehicleName;
			this.vehicleNo = vehicleNo;
			this.vehicleType = vehicleType;
			this.vehicleModel = vehicleModel;
			this.vehiclecapaCity = vehiclecapaCity;
			this.vehiclecurrentCity = vehiclecurrentCity;
			this.vehicleavailabilityStatus = vehicleavailabilityStatus;
			this.pricePerKm = pricePerKm;
			this.averageSpeed = averageSpeed;
		}
		public Vehicle() {
			super();
		}
		@Override
		public String toString() {
			return "Vehicle [id=" + id + ", vehicleName=" + vehicleName + ", vehicleNo=" + vehicleNo + ", vehicleType="
					+ vehicleType + ", vehicleModel=" + vehicleModel + ", vehiclecapaCity=" + vehiclecapaCity
					+ ", vehiclecurrentCity=" + vehiclecurrentCity + ", vehicleavailabilityStatus="
					+ vehicleavailabilityStatus + ", pricePerKm=" + pricePerKm + ", averageSpeed=" + averageSpeed
					+ ", driver=" +"]";
		}   
	
}
		
	    