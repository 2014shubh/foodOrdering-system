package com.impetus.ogos.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int addressId;

	private boolean isActive=true;

	@NotNull
	private int houseNum;

	@NotNull
	private String street;

	@NotNull
	private String area;

	@NotNull
	private String city;

	@NotNull
	private String pincode;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

	public Address(int addressId, @NotNull int houseNum, @NotNull String street, @NotNull String area,
			@NotNull String city, @NotNull String pincode) {
		super();
		this.addressId = addressId;
		this.houseNum = houseNum;
		this.street = street;
		this.area = area;
		this.city = city;
		this.pincode = pincode;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public Address() {
		super();
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
