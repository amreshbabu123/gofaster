package com.quickmove.GoFaster.dto;


public class RegisterDriverVehiclesDto {
	
	private String licenceNo;
	private String upiId;
	private String driverName;
	private int age;
	private Long mobileNo;
	private String gender;
	private String mailId;
	private String vehicleName;
	private String vehicleNo;
	private String vehicleType;
	private String vehicleCapacity;
	private double latitude;
	private double longitude;
	private double pricePerKm;
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public String getUpiId() {
		return upiId;
	}
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Long getMobileNumber() {
		return mobileNo;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNo = mobileNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
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
	public String getVehicleCapacity() {
		return vehicleCapacity;
	}
	public void setVehicleCapacity(String vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
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
	public double getPricePerKm() {
		return pricePerKm;
	}
	public void setPricePerKm(double pricePerKm) {
		this.pricePerKm = pricePerKm;
	}
	public RegisterDriverVehiclesDto(String licenceNo, String upiId, String driverName, int age, Long mobileNo,
			String gender, String mailId, String vehicleName, String vehicleNo, String vehicleType,
			String vehicleCapacity, double latitude, double longitude, double pricePerKm) {
		super();
		this.licenceNo = licenceNo;
		this.upiId = upiId;
		this.driverName = driverName;
		this.age = age;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.mailId = mailId;
		this.vehicleName = vehicleName;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.vehicleCapacity = vehicleCapacity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pricePerKm = pricePerKm;
	}
	public RegisterDriverVehiclesDto() {
		super();
	}
	@Override
	public String toString() {
		return "RegisterDriverVehiclesDto [licenceNo=" + licenceNo + ", upiId=" + upiId + ", driverName=" + driverName
				+ ", age=" + age + ", mobileNo=" + mobileNo + ", gender=" + gender + ", mailId=" + mailId
				+ ", vehicleName=" + vehicleName + ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType
				+ ", vehicleCapacity=" + vehicleCapacity + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", pricePerKm=" + pricePerKm + "]";
	}
		 
}
