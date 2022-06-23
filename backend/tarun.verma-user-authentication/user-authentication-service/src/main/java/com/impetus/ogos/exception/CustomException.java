package com.impetus.ogos.exception;

@SuppressWarnings("serial")
public class CustomException extends IllegalArgumentException {
    public CustomException(String msg) {
        super(msg);
    }
}
