package com.impetus.ogos.dto;

import com.impetus.ogos.model.CartItem;

public class ItemResponse {

	private Integer productId;
	private Integer quantity;
	
	
	public ItemResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ItemResponse(Integer productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public ItemResponse(CartItem item) {
		this.productId = item.getProductId();
		this.quantity = item.getQuantity();
	}
	

	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
