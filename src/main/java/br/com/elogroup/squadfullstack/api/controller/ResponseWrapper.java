package br.com.elogroup.squadfullstack.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.elogroup.squadfullstack.api.exception.ExceptionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper <T> {

	   private HttpStatus status;
	   private Integer statusCode;
	   private String message;
	   private String description;
	   private ExceptionType type;
	   private boolean success;
	   private List<T> data;
}
