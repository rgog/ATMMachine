package com.atm.machine.atmmachine.customExceptionsAndErrors;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorObject {
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	ErrorObject() {
		timestamp = LocalDateTime.now();
	}

	ErrorObject(HttpStatus status, Throwable ex) {
		this();
		this.setStatus(status);
		this.setMessage("Unexpected error");
	}

	ErrorObject(HttpStatus status, String message, Throwable ex) {
		this();
		this.setStatus(status);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
