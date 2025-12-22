package com.quickmove.GoFaster.dto;

import java.util.Arrays;

public class UPIPaymentDto {
	
	private double fare;
	private byte[] qr;
	
	
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public byte[] getQr() {
		return qr;
	}
	public void setQr(byte[] qr) {
		this.qr = qr;
	}
	public UPIPaymentDto(double fare, byte[] qr) {
		super();
		this.fare = fare;
		this.qr = qr;
	}
	public UPIPaymentDto() {
		super();
	}
	@Override
	public String toString() {
		return "UPIPaymentDto [fare=" + fare + ", qr=" + Arrays.toString(qr) + "]";
	}
}
