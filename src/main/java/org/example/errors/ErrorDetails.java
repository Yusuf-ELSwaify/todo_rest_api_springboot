package org.example.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class ErrorDetails {
	private String message;
	private String uri;
	@JsonFormat(pattern = "dd:MM:yyyy hh:mm:ss")
	private Date timestamp;

	public ErrorDetails(String message, String uri) {
		this.message = message;
		this.uri = uri;
		this.timestamp = new Date(System.currentTimeMillis());
	}
}
