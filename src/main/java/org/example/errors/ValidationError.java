package org.example.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ValidationError {
	private List<String> errors;
	private String uri;
	@JsonFormat(pattern = "dd:MM:yyyy hh:mm:ss")
	private Date timestamp;

	public ValidationError() {
		timestamp = new Date(System.currentTimeMillis());
	}
}
