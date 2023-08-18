package com.hitachi.coe.fullstack.exceptions;


public class APIRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public APIRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public APIRequestException(String message) {
		super(message);
	}

	
}
