package com.impetus.ogos.dto;

public class CartItemDto
{
	private String userId;
	private Integer productId;
	
	
	public CartItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CartItemDto(String userId, Integer productId) {
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
