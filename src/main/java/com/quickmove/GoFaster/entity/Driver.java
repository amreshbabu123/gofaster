package com.quickmove.GoFaster.entity;


import java.time.LocalDateTime; 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Driver {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String licenceNo;
	    private String upiId;
	    private String name;
	    private String status="Available";
	    private int age;
	    private Long mobileNo;
	    private String gender;
	    private String mailId;
	    private double latitude;
	    private double longitude;
	    private String currentAddress;
	    private LocalDateTime blockedAt;
	    @OneToOne(cascade = CascadeType.ALL)
	    private Vehicle vehicle;
	    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Booking> bookingList;
	    @OneToOne
	    @JoinColumn(name = "user_id", nullable = true)
	    private Userr user;

	    
	    
		public List<Booking> getBookingList() {
			return bookingList;
		}
		public void setBookingList(List<Booking> bookingList) {
			this.bookingList = bookingList;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Long getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(Long mobileNo) {
			this.mobileNo = mobileNo;
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
		public Vehicle getVehicle() {
			return vehicle;
		}
		public void setVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
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
		public String getCurrentAddress() {
			return currentAddress;
		}
		public void setCurrentAddress(String currentAddress) {
			this.currentAddress = currentAddress;
		}
	    public LocalDateTime getBlockedAt() {
	        return blockedAt;
	    }

	    public void setBlockedAt(LocalDateTime blockedAt) {
	        this.blockedAt = blockedAt;
	    }
	    
	    
		public Userr getUser() {
			return user;
		}
		public void setUser(Userr user) {
			this.user = user;
		}
		public Driver(Long id, String licenceNo, String upiId, String name, String status, int age, Long mobileNo,
				String gender, String mailId, double latitude, double longitude, String currentAddress,
				LocalDateTime blockedAt, Vehicle vehicle, List<Booking> bookingList,Userr user) {
			super();
			this.id = id;
			this.licenceNo = licenceNo;
			this.upiId = upiId;
			this.name = name;
			this.status = status;
			this.age = age;
			this.mobileNo = mobileNo;
			this.gender = gender;
			this.mailId = mailId;
			this.latitude = latitude;
			this.longitude = longitude;
			this.currentAddress = currentAddress;
			this.blockedAt = blockedAt;
			this.vehicle = vehicle;
			this.bookingList = bookingList;
			this.user=user;
		}
		public Driver() {
			super();
		}
		@Override
		public String toString() {
			return "Driver [id=" + id + ", licenceNo=" + licenceNo + ", upiId=" + upiId + ", name=" + name + ", status="
					+ status + ", age=" + age + ", mobileNo=" + mobileNo + ", gender=" + gender + ", mailId=" + mailId
					+ ", latitude=" + latitude + ", longitude=" + longitude + ", currentAddress=" + currentAddress
					+ ", blockedAt=" + blockedAt + ", vehicle=" + vehicle + ", bookingList=" + bookingList + "user="+user+"]"
					+ ", blockedAt=" + blockedAt + ", vehicle=" + vehicle + ", bookingList=" + bookingList + "]";
		}
}
