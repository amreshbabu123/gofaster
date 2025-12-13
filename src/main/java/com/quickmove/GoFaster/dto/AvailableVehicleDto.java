package com.quickmove.GoFaster.dto;

import java.util.List;

public class AvailableVehicleDto {

    private CustomerDto customer;
    private double distance;
    private String source;
    private String destination;
    private List<VehicleDetails> availableVehicles;

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<VehicleDetails> getAvailableVehicles() {
        return availableVehicles;
    }

    public void setAvailableVehicles(List<VehicleDetails> availableVehicles) {
        this.availableVehicles = availableVehicles;
    }
    public AvailableVehicleDto(CustomerDto customer, double distance, String source, String destination,
                               List<VehicleDetails> availableVehicles) {
        this.customer = customer;
        this.distance = distance;
        this.source = source;
        this.destination = destination;
        this.availableVehicles = availableVehicles;
    }

    public AvailableVehicleDto() {}

    @Override
    public String toString() {
        return "AvailableVehicleDto [customer=" + customer +
                ", distance=" + distance + ", source=" + source +
                ", destination=" + destination + "]";
    }
}
