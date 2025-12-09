package com.quickmove.GoFaster.dto;

public class VehicleDetails {

	private VehicleDto vehicle;
    private double fare;
    private double estimatedTime;

    public VehicleDto getVehicle() {
		return vehicle;
	}

	public void setVehicle1(VehicleDto vehicle) {
		this.vehicle = vehicle;
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

    public VehicleDetails(VehicleDto vehicle, double fare, double estimatedTime) {
        this.vehicle = vehicle;
        this.fare = fare;
        this.estimatedTime = estimatedTime;
    }

    public VehicleDetails() {
    }

	@Override
	public String toString() {
		return "VehicleDetails [vehicle=" + vehicle + ", fare=" + fare + ", estimatedTime=" + estimatedTime + "]";
	}

}

