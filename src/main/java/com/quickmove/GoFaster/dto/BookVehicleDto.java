package com.quickmove.GoFaster.dto;

public class BookVehicleDto {
	
	private Long customerMobileNo;
    private Long driverMobileNo;
    private String sourceLocation;
    private String destinationLocation;
    
    
    public Long getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(Long customerMobileNo) {
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
