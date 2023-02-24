package br.com.elogroup.squadfullstack.api.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException implements GenericHttpException {

	private static final long serialVersionUID = -7811376377421246051L;
	
	public ConflictException(String message) {
		super(message);
	}

	public HttpStatus getStatus() {
		return HttpStatus.CONFLICT;
	}
}