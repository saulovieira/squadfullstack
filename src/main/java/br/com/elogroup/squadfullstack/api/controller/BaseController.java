package br.com.elogroup.squadfullstack.api.controller;

import org.springframework.http.HttpStatus;

import br.com.elogroup.squadfullstack.api.exception.ExceptionType;
import br.com.elogroup.squadfullstack.api.model.resourceModel;
import lombok.extern.slf4j.Slf4j;

public class BaseController {

	public BaseController() {
		super();
	}
	
	protected void assemblyResponseSuccessOperation(final ResponseWrapper<? extends resourceModel> responseWrapper) {
		responseWrapper.setStatus(HttpStatus.OK);
		responseWrapper.setStatusCode(HttpStatus.OK.value());
		responseWrapper.setType(ExceptionType.OPERATION);
		responseWrapper.setSuccess(true);
	}
	

}