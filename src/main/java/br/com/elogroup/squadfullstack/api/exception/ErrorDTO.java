package br.com.elogroup.squadfullstack.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
	public String status;
	public String message;
	@JsonInclude(Include.NON_NULL)
	public String detailMessage;
	public String time;
	public ExceptionType type;
	public boolean success;
	public int statusCode; 
	public String path;
	
}