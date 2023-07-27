package org.example.exceptions;

import org.springframework.http.HttpStatus;

public class ElementNotFoundException extends ApiBaseException{
	public ElementNotFoundException(String msg) {
		super(msg);
	}
	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}
}
