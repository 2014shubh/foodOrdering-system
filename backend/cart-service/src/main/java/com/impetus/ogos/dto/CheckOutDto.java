package com.impetus.ogos.dto;

public class CheckOutDto {
	int addressId;
	String paymentId;
	
	
	public CheckOutDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CheckOutDto(int addressId, String paymentId) {
		super();
		this.addressId = addressId;
		this.paymentId = paymentId;
	}


	public int getAddressId() {
		return addressId;
	}


	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}


	public String getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	
}
