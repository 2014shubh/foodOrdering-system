package com.impetus.ogos.dto;

import java.util.List;

public class CartStatusDto {

	private String cartStatus;
	private int issues;
	private List<String> messages;
	
	
	public CartStatusDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CartStatusDto(String cartStatus, int issues, List<String> messages) {
		super();
		this.cartStatus = cartStatus;
		this.issues = issues;
		this.messages = messages;
	}


	public String getCartStatus() {
		return cartStatus;
	}


	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}


	public int getIssues() {
		return issues;
	}


	public void setIssues(int issues) {
		this.issues = issues;
	}


	public List<String> getMessages() {
		return messages;
	}


	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	
	public void addMessage(String message)
	{
		this.messages.add(message);
	}
}
