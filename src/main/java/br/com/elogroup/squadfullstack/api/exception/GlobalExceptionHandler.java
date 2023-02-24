package br.com.elogroup.squadfullstack.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.com.elogroup.squadfullstack.util.MessageUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageUtil message;
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorDTO> generateForbiddenException(ForbiddenException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = fillErrorDto(ex, request);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(BadRequestException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = fillErrorDto(ex, request);
		errorDTO.setType(ExceptionType.VALIDATION);
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(BadCredentialsException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(message.getLocalizedMessage("exception.user.badcredentials"));
		errorDTO.setDetailMessage(ex.toString());
		errorDTO.setStatus(HttpStatus.UNAUTHORIZED.toString());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorDTO.setType(ExceptionType.VALIDATION);
		errorDTO.setPath(request.getServletPath());
		
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(UsernameNotFoundException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(message.getLocalizedMessage("operation.user.findById.notFound"));
		errorDTO.setDetailMessage(ex.toString());
		errorDTO.setStatus(HttpStatus.UNAUTHORIZED.toString());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorDTO.setType(ExceptionType.VALIDATION);
		errorDTO.setPath(request.getServletPath());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.UNAUTHORIZED);
	}
	

	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ErrorDTO> generateNotFound(InsufficientAuthenticationException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorDTO.setType(ExceptionType.VALIDATION);
		errorDTO.setPath(request.getServletPath());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorDTO> generateConflictException(ConflictException ex, HttpServletRequest reques) {
		ErrorDTO errorDTO = fillErrorDto(ex, reques);
		errorDTO.setType(ExceptionType.VALIDATION);
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(ExpiredJwtException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(message.getLocalizedMessage("exception.token.expired"));
		errorDTO.setDetailMessage(ex.getMessage());
		errorDTO.setStatus(HttpStatus.UNAUTHORIZED.toString());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setType(ExceptionType.EXCEPTION);
		
		errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		errorDTO.setPath(request.getServletPath());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.UNAUTHORIZED);
	}	
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(Throwable ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setType(ExceptionType.EXCEPTION);
		
		errorDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorDTO.setPath(request.getServletPath());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(NoHandlerFoundException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(message.getLocalizedMessage("exception.resource.notFound"));
		errorDTO.setDetailMessage(ex.getMessage());
		errorDTO.setStatus(HttpStatus.NOT_FOUND.toString());
		errorDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setType(ExceptionType.EXCEPTION);
		errorDTO.setPath(request.getServletPath());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> generateBadRequestException(NotFoundException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = fillErrorDto(ex, request);
		errorDTO.setType(ExceptionType.VALIDATION);
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ErrorDTO> generateInternalServerErrorException(InternalServerErrorException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = fillErrorDto(ex, request);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorDTO> generateUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = fillErrorDto(ex, request);
		errorDTO.setType(ExceptionType.EXCEPTION);

		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}

	private ErrorDTO fillErrorDto(GenericHttpException ex, HttpServletRequest request) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus()));
		errorDTO.setTime(new Date().toString());
		errorDTO.setSuccess(false);
		errorDTO.setStatusCode(ex.getStatus().value());
		errorDTO.setPath(request.getServletPath());
		
		if(errorDTO.getStatus().startsWith(String.valueOf(HttpStatus.UNAUTHORIZED.value()))) {
			String detailMessage = ((UnauthorizedException)ex).getDetailMessage();
			errorDTO.setDetailMessage(detailMessage);
		}
		return errorDTO;
	}
}