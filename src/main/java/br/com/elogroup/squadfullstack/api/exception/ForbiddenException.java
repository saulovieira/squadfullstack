package br.com.elogroup.squadfullstack.api.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException implements GenericHttpException {

	private static final long serialVersionUID = -7811376377421246051L;
	
	public ForbiddenException(String message) {
		super(message);
	}

	public HttpStatus getStatus() {
		return HttpStatus.FORBIDDEN;
	}
}