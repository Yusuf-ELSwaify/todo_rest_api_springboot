package org.example.handlers;

import org.example.errors.ValidationError;
import org.example.exceptions.ApiBaseException;
import org.example.errors.ErrorDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ApiBaseException.class)
	public ResponseEntity<ErrorDetails> handleApiException(ApiBaseException ex, WebRequest request) {
		ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, ex.getStatusCode());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ValidationError error = new ValidationError();
		error.setUri(request.getDescription(false));
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		error.setErrors(fieldErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList());
		return new ResponseEntity<>(error, ex.getStatusCode());
	}
}
