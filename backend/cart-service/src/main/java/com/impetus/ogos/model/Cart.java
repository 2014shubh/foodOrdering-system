package com.impetus.ogos.model;


import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Cart 
{
	@Id
	private String userId;

	private String inventoryId;
	
	private double totalWeight;
	
	private int totalItems;
	
	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<CartItem> cartItems = new LinkedList<>();
	
	
	//  Constructors


	public Cart() {
	}


	public Cart(String userId, String inventoryId, double totalWeight, int totalItems, List<CartItem> cartItems) {
		super();
		this.userId = userId;
		this.inventoryId = inventoryId;
		this.totalWeight = totalWeight;
		this.totalItems = totalItems;
		this.cartItems = cartItems;
	}

	public Cart(String userId, String inventoryId, double totalWeight, int totalItems) {
		super();
		this.userId = userId;
		this.inventoryId = inventoryId;
		this.totalWeight = totalWeight;
		this.totalItems = totalItems;
	}



	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}


	public double getTotalWeight() {
		return totalWeight;
	}


	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}


	public int getTotalItems() {
		return totalItems;
	}


	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

}
