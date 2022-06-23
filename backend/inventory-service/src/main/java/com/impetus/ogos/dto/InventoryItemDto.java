package com.impetus.ogos.dto;

import com.impetus.ogos.model.InventoryItem;

public class InventoryItemDto 
{
	private int productId;
	private int quantity;
	
	
	public InventoryItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InventoryItemDto(InventoryItem inventoryItem)
	{
		this.productId = inventoryItem.getProductId();
		this.quantity = inventoryItem.getQuantity();
	}
	
	
	public InventoryItemDto(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}

