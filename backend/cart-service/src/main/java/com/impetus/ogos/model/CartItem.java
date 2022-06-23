package com.impetus.ogos.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;


@Entity
public class CartItem 
{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	private int productId;
	
	private int quantity;
	
	private double productWeight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	@JsonIgnore
	private Cart cart;
	

	
	public CartItem()
	{}



	public CartItem( int productId, int quantity, double productWeight, Cart cart) {

		this.productId = productId;
		this.quantity = quantity;
		this.productWeight = productWeight;
		this.cart = cart;
	}
	
	public CartItem( int productId, int quantity, double productWeight) {

		this.productId = productId;
		this.quantity = quantity;
		this.productWeight = productWeight;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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



	public double getProductWeight() {
		return productWeight;
	}



	public void setProductWeight(double productWeight) {
		this.productWeight = productWeight;
	}



	public Cart getCart() {
		return cart;
	}



	public void setCart(Cart cart) {
		this.cart = cart;
	}


	
}
