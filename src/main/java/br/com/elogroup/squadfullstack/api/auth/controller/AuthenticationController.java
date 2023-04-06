package br.com.elogroup.squadfullstack.api.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.elogroup.squadfullstack.api.auth.model.AuthenticationRequest;
import br.com.elogroup.squadfullstack.api.auth.model.AuthenticationResponse;
import br.com.elogroup.squadfullstack.api.auth.repository.AuthenticationService;
import br.com.elogroup.squadfullstack.api.controller.BaseController;
import br.com.elogroup.squadfullstack.api.controller.ResponseWrapper;
import br.com.elogroup.squadfullstack.api.exception.BadRequestException;
import br.com.elogroup.squadfullstack.api.exception.ForbiddenException;
import br.com.elogroup.squadfullstack.api.exception.InternalServerErrorException;
import br.com.elogroup.squadfullstack.api.exception.NotFoundException;
import br.com.elogroup.squadfullstack.api.model.UserRequest;
import br.com.elogroup.squadfullstack.api.model.UserResponse;
import br.com.elogroup.squadfullstack.domain.model.UserDetail;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

	private final AuthenticationService service;
	private final ModelMapper modelMapper;
	private final LocalValidatorFactoryBean beanValidator;
	private final MessageUtil msgUtil;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequest request) {
		
		try {
			Set<ConstraintViolation<UserRequest>> validate = beanValidator.validate(request);

			if (!validate.isEmpty()) {
				throw new BadRequestException (
						validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage()))
						.collect(Collectors.toList()).toString());
			} else {
				return ResponseEntity.ok(service.register(request));
			}

		} catch (ForbiddenException | DataIntegrityViolationException e) {
			throw new ForbiddenException(e.getMessage());
		} catch (EntityNotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (BadRequestException e) {
			throw e ;
		} catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	
		Set<ConstraintViolation<AuthenticationRequest>> validate = beanValidator.validate(request);
		if (!validate.isEmpty()) {
			throw new BadRequestException (
					validate.stream()
					.map(mess -> String.format("'%s'", mess.getMessage()))
					.collect(Collectors.toList()).toString());
			
		} else {
			return ResponseEntity.ok(service.authenticate(request));
		}
	}
	
	@GetMapping()
	@ResponseBody
	public ResponseWrapper<UserResponse> getAll() {
		ResponseWrapper<UserResponse> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setData(new ArrayList<>());
		List<UserDetail> users = service.getAll();
		responseWrapper.setData(new ArrayList<>());
		assemblyResponseSuccessOperation(responseWrapper);

		if (users != null && !users.isEmpty()) {
			List<UserResponse> usersDto = users.stream().map(this::toUserResponse).collect(Collectors.toList());
			responseWrapper.setData(usersDto);
			
		}else  {
			responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.user.findAll.notFound"));
		}
		return responseWrapper;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<UserResponse> getById(@PathVariable Long id) {
		ResponseWrapper<UserResponse> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setData(new ArrayList<>());
		UserDetail user = service.getById(id);
		responseWrapper.setData(new ArrayList<>());
		assemblyResponseSuccessOperation(responseWrapper);

		if (user != null) {
			responseWrapper.setData(Collections.singletonList( modelMapper.map(user, UserResponse.class)));
		} else {				
			responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.user.findById.notFound"));
			responseWrapper.setSuccess(false);
		}
		return responseWrapper;
	}
	

	@PutMapping
	@ResponseBody
	public ResponseWrapper<UserResponse> change(@RequestBody UserRequest userInput) {
		ResponseWrapper<UserResponse> responseWrapper = new ResponseWrapper<>();

		Set<ConstraintViolation<UserRequest>> validate = beanValidator.validate(userInput);

		if (!validate.isEmpty()) {
			throw new BadRequestException (validate.stream()
					.map(mess -> String.format("'%s'", mess.getMessage()))
					.collect(Collectors.toList()).toString());
		} else {

			UserResponse user = modelMapper.map(userInput, UserResponse.class);
			UserResponse userDto = toUserResponse(service.update(user));

			assemblyResponseSuccessOperation(responseWrapper);
			responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.user.change.success", user.getEmail()));
			responseWrapper.setData(Arrays.asList(userDto));
		}
		return responseWrapper;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<UserResponse> delete(@PathVariable Long id) {
		ResponseWrapper<UserResponse> responseWrapper = new ResponseWrapper<>();
		responseWrapper.setData(new ArrayList<>());

		UserDetail user = service.delete(id);
		assemblyResponseSuccessOperation(responseWrapper);
		responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.user.delete.success", user.getEmail()));

		return responseWrapper;
	}

	private UserResponse toUserResponse(UserDetail user) {
		return modelMapper.map(user, UserResponse.class);
	}
}