package com.quickmove.GoFaster.entity;

import jakarta.persistence.*;

@Entity
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String paymentMode;   
    private String paymentStatus; 

    @OneToOne(cascade = CascadeType.ALL)
    private Booking booking;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vehicle vehicle;
    
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Payments() {}

    public Payments(Long id, double amount, String paymentMode, String paymentStatus,
                    Booking booking, Customer customer, Vehicle vehicle) {
        this.id = id;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.booking = booking;
        this.customer = customer;
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Payments [id=" + id + ", amount=" + amount +
                ", paymentMode=" + paymentMode +
                ", paymentStatus=" + paymentStatus + "]";
    }
}
