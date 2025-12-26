package com.quickmove.GoFaster.dto;

public class VehicleDto {
	   private Long id;
	    private String vehicleName;
	    private String vehicleNo;
	    private String vehicleType;
	    private String vehicleModel;
	    private String vehiclecapaCity;
	    private String vehiclecurrentCity;
	    private String vehicleavailabilityStatus="Available";
	    private double pricePerKm;
	    private int averageSpeed;
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
	    
	    
	}

