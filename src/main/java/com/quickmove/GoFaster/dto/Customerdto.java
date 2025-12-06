package com.quickmove.GoFaster.dto;

public class Customerdto 
{
private String name;
private int age;
private char  gender;
private long mobileNo;
private String email;
private  double latitude;
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
public char getGender() {
	return gender;
}
public void setGender(char gender) {
	this.gender = gender;
}
public long getMobileNo() {
	return mobileNo;
}
public void setMobileNo(long mobileNo) {
	this.mobileNo = mobileNo;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
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
public Customerdto(String name, int age, char gender, long mobileNo, String email, double latitude, double longitude) {
	super();
	this.name = name;
	this.age = age;
	this.gender = gender;
	this.mobileNo = mobileNo;
	this.email = email;
	this.latitude = latitude;
	this.longitude = longitude;
}
public Customerdto() {
	super();
}
@Override
public String toString() {
	return "Customerdto [name=" + name + ", age=" + age + ", gender=" + gender + ", mobileNo=" + mobileNo + ", email="
			+ email + ", latitude=" + latitude + ", longitude=" + longitude + "]";
}


}