package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApiBaseException {
	public AlreadyExistsException(String msg) {
		super(msg);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.CONFLICT;
	}
}
