package br.com.elogroup.squadfullstack.api.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RuntimeException implements GenericHttpException {

	private static final long serialVersionUID = -7811376377421246051L;

	public InternalServerErrorException(String message) {
		super(message);
	}

	public HttpStatus getStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}