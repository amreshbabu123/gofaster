package com.quickmove.GoFaster.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructureList<T> {
	
	private int statuscode;
	private String message;
	private List<T> data;
	
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	

}
