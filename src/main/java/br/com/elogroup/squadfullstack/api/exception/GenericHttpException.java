package br.com.elogroup.squadfullstack.api.exception;

import org.springframework.http.HttpStatus;

public interface GenericHttpException {

	HttpStatus getStatus();

	String getMessage();
}
