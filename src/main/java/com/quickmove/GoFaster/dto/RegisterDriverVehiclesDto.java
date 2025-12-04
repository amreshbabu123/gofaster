package com.quickmove.GoFaster.dto;

public class RegisterDriverVehiclesDto
{
	private int licenceNo;
	private int upiid;
	private String drivername;
	private int age;
	private long moubilenumber;
	private Character gender;
	private String mailid;
	private String vehiclename;
	private int vehicleno;
	private String vehicletype;
	private int vehicles;
	private String vehiclecapacity;
	private double latitude;
	private double longitude;
	public int getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(int licenceNo) {
		this.licenceNo = licenceNo;
	}
	public int getUpiid() {
		return upiid;
	}
	public void setUpiid(int upiid) {
		this.upiid = upiid;
	}
	public String getDrivername() {
		return drivername;
	}
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getMoubilenumber() {
		return moubilenumber;
	}
	public void setMoubilenumber(long moubilenumber) {
		this.moubilenumber = moubilenumber;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getVehiclename() {
		return vehiclename;
	}
	public void setVehiclename(String vehiclename) {
		this.vehiclename = vehiclename;
	}
	public int getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(int vehicleno) {
		this.vehicleno = vehicleno;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public int getVehicles() {
		return vehicles;
	}
	public void setVehicles(int vehicles) {
		this.vehicles = vehicles;
	}
	public String getVehiclecapacity() {
		return vehiclecapacity;
	}
	public void setVehiclecapacity(String vehiclecapacity) {
		this.vehiclecapacity = vehiclecapacity;
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
	public RegisterDriverVehiclesDto(int licenceNo, int upiid, String drivername, int age, long moubilenumber,
			Character gender, String mailid, String vehiclename, int vehicleno, String vehicletype, int vehicles,
			String vehiclecapacity, double latitude, double longitude) {
		super();
		this.licenceNo = licenceNo;
		this.upiid = upiid;
		this.drivername = drivername;
		this.age = age;
		this.moubilenumber = moubilenumber;
		this.gender = gender;
		this.mailid = mailid;
		this.vehiclename = vehiclename;
		this.vehicleno = vehicleno;
		this.vehicletype = vehicletype;
		this.vehicles = vehicles;
		this.vehiclecapacity = vehiclecapacity;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public RegisterDriverVehiclesDto() {
		super();
	}
	@Override
	public String toString() {
		return "RegisterDriverVehiclesDTO [licenceNo=" + licenceNo + ", upiid=" + upiid + ", drivername=" + drivername
				+ ", age=" + age + ", moubilenumber=" + moubilenumber + ", gender=" + gender + ", mailid=" + mailid
				+ ", vehiclename=" + vehiclename + ", vehicleno=" + vehicleno + ", vehicletype=" + vehicletype
				+ ", vehicles=" + vehicles + ", vehiclecapacity=" + vehiclecapacity + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
}
