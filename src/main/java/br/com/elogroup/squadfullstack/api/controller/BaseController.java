package br.com.elogroup.squadfullstack.api.controller;

import org.springframework.http.HttpStatus;

import br.com.elogroup.squadfullstack.api.exception.ExceptionType;
import br.com.elogroup.squadfullstack.api.model.ResourceModel;

public class BaseController {

	public BaseController() {
		super();
	}
	
	protected void assemblyResponseSuccessOperation(final ResponseWrapper<? extends ResourceModel> responseWrapper) {
		responseWrapper.setStatus(HttpStatus.OK);
		responseWrapper.setStatusCode(HttpStatus.OK.value());
		responseWrapper.setType(ExceptionType.OPERATION);
		responseWrapper.setSuccess(true);
	}
	

}