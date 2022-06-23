package com.impetus.ogos.exception;

public class IdConflictException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public IdConflictException(String message)
	{
		super(message);
	}
}
