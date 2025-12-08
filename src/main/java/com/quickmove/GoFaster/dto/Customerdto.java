package com.quickmove.GoFaster.dto;

public class CustomerDto {
	
	private String name;
    private int age;
    private String gender;
    private long mobileNo;
    private String emailId;
    private double latitude;
    private double longitude;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public CustomerDto(String name, int age, String gender, long mobileNo, String emailId, double latitude,
			double longitude) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "CustomerDto [name=" + name + ", age=" + age + ", gender=" + gender + ", mobileNo=" + mobileNo
				+ ", emailId=" + emailId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
    


}
