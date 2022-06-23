package com.impetus.ogos.exception;

import javax.naming.AuthenticationException;

@SuppressWarnings("serial")
public class JwtTokenMalformedException extends AuthenticationException {

	public JwtTokenMalformedException(String msg) {
		super(msg);
	}
}
