package com.quickmove.GoFaster.dto;

public class CurrentLocationDTO {
	private double latitude;
    private double longitude;
    private Long driverMobileNo; 
    private Long customerMobileNo;
    

    public Long getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(Long driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}

	public Long getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(Long customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}

	public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
