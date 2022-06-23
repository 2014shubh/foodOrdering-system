package com.impetus.ogos.dto;

import java.util.LinkedList;
import java.util.List;

public class CartDto
{
	private List<CartItemDto> items = new LinkedList<>();
	
	public CartDto()
	{}

	public CartDto(List<CartItemDto> items) {
		super();
		this.items = items;
	}

	public List<CartItemDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}


}
