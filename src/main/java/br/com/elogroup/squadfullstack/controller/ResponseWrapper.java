package br.com.elogroup.squadfullstack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper <T> {

	   private HttpStatus status;
	   private Integer statusCode;
	   private String message;
	   private String description;
	   private List<T> data;
}
