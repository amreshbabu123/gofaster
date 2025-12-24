package com.quickmove.GoFaster.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import jakarta.persistence.*;


@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceLocation;
    private String destinationLocation;
    private double distanceTravelled;
    private double fare;
    private double estimatedTimeRequired;
    private LocalDateTime bookingDate;
    private String bookingStatus;
    private String paymentStatus = "not paid";
    
    @Column(nullable = true)
    @JsonIgnore
    private Integer deliveryOtp ;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;      

    @ManyToOne
    private Vehicle vehicle;     

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payments payment;  
    
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getEstimatedTimeRequired() {
        return estimatedTimeRequired;
    }

    public void setEstimatedTimeRequired(double estimatedTimeHrs) {
        this.estimatedTimeRequired = estimatedTimeHrs;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Payments getPayment() {
        return payment;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }
    
    

	public Integer getDeliveryOtp() {
		return deliveryOtp;
	}

	public void setDeliveryOtp(Integer deliveryOtp) {
		this.deliveryOtp = deliveryOtp;
	}

	public Booking(Long id, String sourceLocation, String destinationLocation, double distanceTravelled, double fare,
			double estimatedTimeRequired, LocalDateTime bookingDate, String bookingStatus, String paymentStatus,
			Customer customer, Driver driver, Vehicle vehicle, Payments payment) {
		super();
		this.id = id;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.distanceTravelled = distanceTravelled;
		this.fare = fare;
		this.estimatedTimeRequired = estimatedTimeRequired;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.paymentStatus = paymentStatus;
		this.customer = customer;
		this.driver = driver;
		this.vehicle = vehicle;
		this.payment = payment;
	}

	public Booking() {
		super();
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", sourceLocation=" + sourceLocation + ", destinationLocation="
				+ destinationLocation + ", distanceTravelled=" + distanceTravelled + ", fare=" + fare
				+ ", estimatedTimeRequired=" + estimatedTimeRequired + ", bookingDate=" + bookingDate
				+ ", bookingStatus=" + bookingStatus + ", paymentStatus=" + paymentStatus + ", customer=" + customer
				+ ", driver=" + driver + ", vehicle=" + vehicle + ", payment=" + payment + ",deliveryOtp:"+deliveryOtp+"]";
	}
    
}
