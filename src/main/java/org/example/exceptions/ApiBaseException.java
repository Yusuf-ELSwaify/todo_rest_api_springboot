package org.example.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiBaseException extends RuntimeException {
	public ApiBaseException(String msg) {
		super(msg);
	}
	public abstract HttpStatus getStatusCode();
}
