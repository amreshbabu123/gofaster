package com.quickmove.GoFaster.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	    private String mobileNo;
	    private String gender;
	    private String mailId;
	    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
	    @JsonManagedReference
	    private Vehicle vehicle;

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
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
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

		
		
		public Driver(Long id, String licenceNo, String upiId, String name, String status, int age, String mobileNo,
				String gender, String mailId, Vehicle vehicle) {
			super();
			
			this.licenceNo = licenceNo;
			this.upiId = upiId;
			this.name = name;
			this.status = status;
			this.age = age;
			this.mobileNo = mobileNo;
			this.gender = gender;
			this.mailId = mailId;
			this.vehicle = vehicle;
		}
		public Driver() {
			super();
		}
		@Override
		public String toString() {
			return "Driver [id=" + id + ", licenceNo=" + licenceNo + ", upiId=" + upiId + ", name=" + name + ", status="
					+ status + ", age=" + age + ", mobileNo=" + mobileNo + ", gender=" + gender + ", mailId=" + mailId
					+ ", vehicle=" + vehicle + "]";
		}
}
