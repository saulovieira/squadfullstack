package br.com.elogroup.squadfullstack.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException implements GenericHttpException {

	private static final long serialVersionUID = -7811376377421246051L;
	
	private String detailMessage;

	public UnauthorizedException(String message) {
		super(message);
	}

	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}
	
}