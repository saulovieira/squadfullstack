package br.com.elogroup.squadfullstack.api.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorDTO> generateForbiddenException(ForbiddenException ex) {
		ErrorDTO errorDTO = fillErrorDto(ex);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(BadRequestException ex) {
		ErrorDTO errorDTO = fillErrorDto(ex);
		errorDTO.setType(ExceptionType.VALIDATION);
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ErrorDTO> generateInternalServerErrorException(InternalServerErrorException ex) {
		ErrorDTO errorDTO = fillErrorDto(ex);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorDTO> generateUnauthorizedException(UnauthorizedException ex) {
		ErrorDTO errorDTO = fillErrorDto(ex);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	private ErrorDTO fillErrorDto(GenericHttpException ex) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus()));
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		return errorDTO;
	}
}