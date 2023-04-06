package br.com.elogroup.squadfullstack.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
	private String status;
	private String message;
	@JsonInclude(Include.NON_NULL)
	private String detailMessage;
	private String time;
	private ExceptionType type;
	private boolean success;
	private int statusCode; 
	private String path;
}