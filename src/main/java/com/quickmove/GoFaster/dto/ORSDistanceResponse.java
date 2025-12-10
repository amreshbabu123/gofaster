package com.quickmove.GoFaster.dto;

public class ORSDistanceResponse {
	  public double distanceKm;
	    public double timeHours;

	    public double getDistanceKm() {
			return distanceKm;
		}
		public void setDistanceKm(double distanceKm) {
			this.distanceKm = distanceKm;
		}
		public double getTimeHours() {
			return timeHours;
		}
		public void setTimeHours(double timeHours) {
			this.timeHours = timeHours;
		}
		public ORSDistanceResponse(double distanceKm, double timeHours) {
	        this.distanceKm = distanceKm;
	        this.timeHours = timeHours;
	    }
}
