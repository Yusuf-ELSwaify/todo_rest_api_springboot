package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthenticatedUserException extends ApiBaseException {
	public NotAuthenticatedUserException(String msg) {
		super(msg);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.UNAUTHORIZED;
	}
}
