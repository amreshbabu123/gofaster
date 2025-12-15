package com.quickmove.GoFaster.dto;

public class RideDetailsDto {
	private String fromLocation;
	private String toLocation;
	private double distance;
	private double fare;
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public RideDetailsDto(String fromLocation, String toLocation, double distance, double fare) {
		super();
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.distance = distance;
		this.fare = fare;
	}
	public RideDetailsDto() {
		super();
	}
	@Override
	public String toString() {
		return "RideDetailsDto [fromLocation=" + fromLocation + ", toLocation=" + toLocation + ", distance=" + distance
				+ ", fare=" + fare + "]";
	}
}
