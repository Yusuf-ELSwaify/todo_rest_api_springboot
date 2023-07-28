package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class NotOwnerException extends ApiBaseException {
	public NotOwnerException(String msg) {
		super(msg);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.FORBIDDEN;
	}
}
