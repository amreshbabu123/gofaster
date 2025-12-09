package com.quickmove.GoFaster.dto;

public class BookVehicleDto {
	
	private String customerMobileNo;
    private Long driverMobileNo;
    private String sourceLocation;
    private String destinationLocation;

    // --- Getters & Setters ---
    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public Long getDriverMobileNo() {
        return driverMobileNo;
    }

    public void setDriverMobileNo(Long driverMobileNo) {
        this.driverMobileNo = driverMobileNo;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

	

}
