package br.com.elogroup.squadfullstack.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.elogroup.squadfullstack.api.model.UserToCreateOrUpdate;
import br.com.elogroup.squadfullstack.api.model.UserToList;
import br.com.elogroup.squadfullstack.domain.model.User;
import br.com.elogroup.squadfullstack.domain.sevice.LoginService;
import br.com.elogroup.squadfullstack.domain.util.ExceptionType;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LocalValidatorFactoryBean beanValidator;
	
	@Autowired
	private MessageUtil msgUtil;
	
	@GetMapping()
	@ResponseBody
	public ResponseWrapper<UserToList> getAll(){
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());
		try {
			List<User> users = loginService.getUsers();
			responseWrapper.setData(new ArrayList<UserToList>());
			
			if(users != null) {
				
				List<UserToList> usersDto = users
						.stream()
						.map(this::toUserToList)
						.collect(Collectors.toList());
				
				responseWrapper.setData(usersDto);
			}
			
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			if(users.isEmpty()) {
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.findAll.notFound"));
			}
			responseWrapper.setType(ExceptionType.OPERATION);
			
		} catch (Throwable e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.EXCEPTION);
			
		}
		return responseWrapper;
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseWrapper<UserToList> create(@Valid @RequestBody UserToCreateOrUpdate userToCreate){
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());

		try {			
			User user = modelMapper.map(userToCreate, User.class);
			UserToList userDto = toUserToList(loginService.create(user));
			
			responseWrapper.setData(Arrays.asList(userDto));
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.create.success", user.getUsername()));
			responseWrapper.setType(ExceptionType.OPERATION);
			
		} catch(DuplicateKeyException e) {
			responseWrapper.setStatus(HttpStatus.BAD_REQUEST);
			responseWrapper.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.VALIDATION);
			
		} catch (ConstraintViolationException e) {
			User user = modelMapper.map(userToCreate, User.class);
			Set<ConstraintViolation<User>> validate = beanValidator.validate(user);
			responseWrapper.setStatus(HttpStatus.BAD_REQUEST);
			responseWrapper.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseWrapper.setMessage(validate.stream()
					.map(mess -> String.format("'%s'", mess.getMessage().toString()))
					.collect(Collectors.toList()).toString());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.VALIDATION);
			
		} catch (Throwable e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.EXCEPTION);
			
		}
		return responseWrapper;
	}
	
	@PutMapping
	@ResponseBody
	public ResponseWrapper<UserToList> change(@Valid @RequestBody UserToCreateOrUpdate userToCreate){
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());

		try {			
			User user = modelMapper.map(userToCreate, User.class);
			UserToList userDto = toUserToList(loginService.change(user));
			
			responseWrapper.setData(Arrays.asList(userDto));
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.change.success", user.getUsername()));
			responseWrapper.setType(ExceptionType.OPERATION);
			
		} catch (ConstraintViolationException e) {
			User user = modelMapper.map(userToCreate, User.class);
			Set<ConstraintViolation<User>> validate = beanValidator.validate(user);
			responseWrapper.setStatus(HttpStatus.BAD_REQUEST);
			responseWrapper.setStatusCode(HttpStatus.BAD_REQUEST.value());
			responseWrapper.setMessage(validate.stream()
					.map(mess -> String.format("'%s'", mess.getMessage().toString()))
					.collect(Collectors.toList()).toString());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.VALIDATION);
			
		} catch (Throwable e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.EXCEPTION);
			
		}
		return responseWrapper;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<UserToList> delete(@PathVariable Long id){
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());

		try {			
			User user = loginService.delete(id);
			
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.delete.success", user.getUsername()));
			responseWrapper.setType(ExceptionType.OPERATION);
			
		} catch (Throwable e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
			responseWrapper.setType(ExceptionType.EXCEPTION);
			
		}
		return responseWrapper;
	}
	
	
	private UserToList toUserToList(User user){
		return modelMapper.map(user, UserToList.class);
	}
	
}
