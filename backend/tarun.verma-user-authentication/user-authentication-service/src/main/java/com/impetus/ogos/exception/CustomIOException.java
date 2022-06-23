package com.impetus.ogos.exception;

@SuppressWarnings("serial")
public class CustomIOException extends IllegalArgumentException{

	public CustomIOException(Exception e) {
        super(e);
    }
}
