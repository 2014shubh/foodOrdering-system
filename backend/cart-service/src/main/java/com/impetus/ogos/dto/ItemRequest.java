package com.impetus.ogos.dto;

public class ItemRequest {

	private String userId;
	private Integer productId;
	
	
	public ItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ItemRequest(String userId, Integer productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
