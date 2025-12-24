package com.quickmove.GoFaster.entity;

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
public class Customer {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private int age;
	    private String gender;
	    private Long mobileNo;
	    private String emailId;
	    private double latitude;
	    private double longitude;
	    private String currentLocation;
        // Customer has multiple bookings
	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Booking> bookingList;

	    private boolean activeBookingFlag = false;
	    
	    private Double penalty=(double) 0;
	    
	    @OneToOne
	    @JoinColumn(name = "user_id", nullable = true)
	    private Userr user;


	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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
		public Long getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(Long mobileNo) {
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
		public String getCurrentLocation() {
			return currentLocation;
		}
		public void setCurrentLocation(String currentLocation) {
			this.currentLocation = currentLocation;
		}
		public List<Booking> getBookingList() {
			return bookingList;
		}
		public void setBookingList(List<Booking> bookingList) {
			this.bookingList = bookingList;
		}
		public boolean isActiveBookingFlag() {
	        return activeBookingFlag;
	    }

	    public void setActiveBookingFlag(boolean activeBookingFlag) {
	        this.activeBookingFlag = activeBookingFlag;
	    }
	    
	    
		public Double getPenalty() {
			return penalty;
		}
		public void setPenalty(Double penalty) {
			this.penalty = penalty;
		}
		
		
		
		public Userr getUser() {
			return user;
		}
		public void setUser(Userr user) {
			this.user = user;
		}
		public Customer(Long id, String name, int age, String gender, Long mobileNo, String emailId, double latitude,
				double longitude, String currentLocation, List<Booking> bookingList, boolean activeBookingFlag,
				Double penalty,Userr user) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
			this.gender = gender;
			this.mobileNo = mobileNo;
			this.emailId = emailId;
			this.latitude = latitude;
			this.longitude = longitude;
			this.currentLocation = currentLocation;
			this.bookingList = bookingList;
			this.activeBookingFlag = activeBookingFlag;
			this.penalty = penalty;
			this.user=user;
		}
		public Customer() {
			super();
		}
		@Override
		public String toString() {
			return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", mobileNo="
					+ mobileNo + ", emailId=" + emailId + ", latitude=" + latitude + ", longitude=" + longitude
					+ ", currentLocation=" + currentLocation + ", bookingList=" + bookingList + ", activeBookingFlag="
					+ activeBookingFlag + ",penalty="+penalty+"User="+user+"]";
		}
}
