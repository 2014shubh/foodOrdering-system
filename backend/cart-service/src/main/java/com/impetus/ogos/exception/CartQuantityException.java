package com.impetus.ogos.exception;

public class CartQuantityException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public CartQuantityException(String message)
	{
		super(message);
	}
}
