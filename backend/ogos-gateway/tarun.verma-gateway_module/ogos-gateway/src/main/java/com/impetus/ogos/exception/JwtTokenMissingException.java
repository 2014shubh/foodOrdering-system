package com.impetus.ogos.exception;

import javax.naming.AuthenticationException;

@SuppressWarnings("serial")
public class JwtTokenMissingException extends AuthenticationException {
	
	public JwtTokenMissingException(String msg) {
		super(msg);
	}
}
