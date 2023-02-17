package br.com.elogroup.squadfullstack.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
	public String status;
	public String message;
	public String time;
	public ExceptionType type;
	public boolean success;

}