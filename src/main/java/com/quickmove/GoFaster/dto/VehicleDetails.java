package com.quickmove.GoFaster.dto;

public class VehicleDetails {

	private VehicleDto vehicle;
	private Long driverMobileNo;
    private double fare;
    private double estimatedTime;

    public VehicleDto getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleDto vehicle) {
		this.vehicle = vehicle;
	}
	public Long getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(Long driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}

	public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

	public VehicleDetails(VehicleDto vehicle, Long driverMobileNo, double fare, double estimatedTime) {
		super();
		this.vehicle = vehicle;
		this.driverMobileNo = driverMobileNo;
		this.fare = fare;
		this.estimatedTime = estimatedTime;
	}

	public VehicleDetails() {
		super();
	}

	@Override
	public String toString() {
		return "VehicleDetails [vehicle=" + vehicle + ", driverMobileNo=" + driverMobileNo + ", fare=" + fare
				+ ", estimatedTime=" + estimatedTime + "]";
	}
}

