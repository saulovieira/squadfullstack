package br.com.elogroup.squadfullstack.api.controller;

import org.springframework.http.HttpStatus;

import br.com.elogroup.squadfullstack.api.model.InputModel;
import br.com.elogroup.squadfullstack.domain.util.ExceptionType;

public class BaseController {

	public BaseController() {
		super();
	}

	void assemblyResponseToInvalidInput(ResponseWrapper<? extends InputModel> responseWrapper) {
		responseWrapper.setStatus(HttpStatus.BAD_REQUEST);
		responseWrapper.setStatusCode(HttpStatus.BAD_REQUEST.value());
		responseWrapper.setType(ExceptionType.VALIDATION);
		responseWrapper.setSuccess(false);
	}
	
	void assembyResponseInternalServerError(ResponseWrapper<? extends InputModel> responseWrapper, Throwable e) {
		responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseWrapper.setMessage(e.getMessage());
		responseWrapper.setDescription(e.toString());
		responseWrapper.setType(ExceptionType.EXCEPTION);
		responseWrapper.setSuccess(false);
	}
	
	void assemblyResponseSuccessOperation(final ResponseWrapper<? extends InputModel> responseWrapper) {
		responseWrapper.setStatus(HttpStatus.OK);
		responseWrapper.setStatusCode(HttpStatus.OK.value());
		responseWrapper.setType(ExceptionType.OPERATION);
		responseWrapper.setSuccess(true);
	}
	
	void assemblyResponseToBadRequest(ResponseWrapper<? extends InputModel> responseWrapper, Throwable e) {
		responseWrapper.setStatus(HttpStatus.BAD_REQUEST);
		responseWrapper.setStatusCode(HttpStatus.BAD_REQUEST.value());
		responseWrapper.setMessage(e.getMessage());
		responseWrapper.setDescription(e.toString());
		responseWrapper.setType(ExceptionType.VALIDATION);
		responseWrapper.setSuccess(false);
	}
}