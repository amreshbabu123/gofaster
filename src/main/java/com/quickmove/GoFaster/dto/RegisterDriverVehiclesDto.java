package com.quickmove.GoFaster.dto;

public class RegisterDriverVehiclesDto
{
	private int licenceNo;
	private int upiId;
	private String driverName;
	private int age;
	private long mobileNumber;
	private Character gender;
	private String mailId;
	private String vehicleName;
	private int vehicleNo;
	private String vehicleType;
	private int vehicles;
	private String vehicleCapacity;
	private double latitude;
	private double longitude;
	private String pricePerKm;
	public int getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(int licenceNo) {
		this.licenceNo = licenceNo;
	}
	public int getUpiId() {
		return upiId;
	}
	public void setUpiId(int upiId) {
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
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
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
	public int getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(int vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public int getVehicles() {
		return vehicles;
	}
	public void setVehicles(int vehicles) {
		this.vehicles = vehicles;
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
	public String getPricePerKm() {
		return pricePerKm;
	}
	public void setPricePerKm(String pricePerKm) {
		this.pricePerKm = pricePerKm;
	}
	public RegisterDriverVehiclesDto(int licenceNo, int upiId, String driverName, int age, long mobileNumber,
			Character gender, String mailId, String vehicleName, int vehicleNo, String vehicleType, int vehicles,
			String vehicleCapacity, double latitude, double longitude, String pricePerKm) {
		super();
		this.licenceNo = licenceNo;
		this.upiId = upiId;
		this.driverName = driverName;
		this.age = age;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.mailId = mailId;
		this.vehicleName = vehicleName;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.vehicles = vehicles;
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
				+ ", age=" + age + ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", mailId=" + mailId
				+ ", vehicleName=" + vehicleName + ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType
				+ ", vehicles=" + vehicles + ", vehicleCapacity=" + vehicleCapacity + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", pricePerKm=" + pricePerKm + "]";
	}
	
	 
}
