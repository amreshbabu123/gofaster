package com.quickmove.GoFaster.dto;

import java.util.List;

public class BookingHistoryDto {
	
	private List<RideDetailsDto> history;
	private double toatalAmount;

	public List<RideDetailsDto> getHistory() {
		return history;
	}
	public void setHistory(List<RideDetailsDto> history) {
		this.history = history;
	}
	public double getToatalAmount() {
		return toatalAmount;
	}
	public void setToatalAmount(double toatalAmount) {
		this.toatalAmount = toatalAmount;
	}
	public BookingHistoryDto(List<RideDetailsDto> history, double toatalAmount) {
		super();
		this.history = history;
		this.toatalAmount = toatalAmount;
	}
	public BookingHistoryDto() {
		super();
	}
	@Override
	public String toString() {
		return "BookingHistoryDto [history=" + history + ", toatalAmount=" + toatalAmount + "]";
	}
}
