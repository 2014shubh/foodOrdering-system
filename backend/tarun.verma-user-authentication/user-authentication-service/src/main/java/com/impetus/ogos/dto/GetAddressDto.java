package com.impetus.ogos.dto;

import com.impetus.ogos.models.Address;

public class GetAddressDto {

	private boolean isActive;
	private int addressId;
	
	private int houseNum;
	
	private String street;
	
	private String area;
	
	private String city;
	
	private String pincode;
	
	public GetAddressDto() {}
	
	public GetAddressDto(Address address) {
		this.addressId=address.getAddressId();
		this.houseNum=address.getHouseNum();
		this.street=address.getStreet();
		this.area=address.getArea();
		this.city=address.getCity();
		this.pincode=address.getPincode();
		this.isActive=address.isActive();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	
}
