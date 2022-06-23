package com.impetus.ogos.model;

import java.util.LinkedList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Inventory 
{
	@Id
	private String inventoryId;
//	private String managerId;
//	private String deliveryPartnerId;
	

	@OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<InventoryItem> InventoryItems = new LinkedList<>();


	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Inventory(String inventoryId, List<InventoryItem> inventoryItems) {
		super();
		this.inventoryId = inventoryId;
		InventoryItems = inventoryItems;
	}


	public String getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}


/*	public String getManagerId() {
		return managerId;
	}


	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}


	public String getDeliveryPartnerId() {
		return deliveryPartnerId;
	}


	public void setDeliveryPartnerId(String deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	} */


	public List<InventoryItem> getInventoryItems() {
		return InventoryItems;
	}


	public void setInventoryItems(List<InventoryItem> inventoryItems) {
		InventoryItems = inventoryItems;
	}
	
}
