package com.impetus.ogos.dto;

import java.util.LinkedList;
import java.util.List;

public class InventoryDto 
{
	
	private String InventoryId;
	
	List<InventoryItemDto> inventoryItems = new LinkedList<>();

	public InventoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryDto(String inventoryId, List<InventoryItemDto> inventoryItems) {
		super();
		InventoryId = inventoryId;
		this.inventoryItems = inventoryItems;
	}

	public String getInventoryId() {
		return InventoryId;
	}

	public void setInventoryId(String inventoryId) {
		InventoryId = inventoryId;
	}


	public List<InventoryItemDto> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(List<InventoryItemDto> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}


	

}
