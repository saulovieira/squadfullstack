package br.com.elogroup.squadfullstack.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import br.com.elogroup.squadfullstack.api.model.UserToList;
import br.com.elogroup.squadfullstack.api.model.UserToUpdate;
import br.com.elogroup.squadfullstack.domain.model.User;
import br.com.elogroup.squadfullstack.domain.sevice.LoginService;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.ConstraintViolation;

@Controller
@RequestMapping("/api/user")
public class LoginController extends BaseController {

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
	public ResponseWrapper<UserToList> getAll() {
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());
		try {
			List<User> users = loginService.getUsers();
			responseWrapper.setData(new ArrayList<UserToList>());
			assemblyResponseSuccessOperation(responseWrapper);

			if (users != null && !users.isEmpty()) {
				List<UserToList> usersDto = users.stream().map(this::toUserToList).collect(Collectors.toList());
				responseWrapper.setData(usersDto);
				
			}else  {
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.findAll.notFound"));
			}

		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<UserToList> getById(@PathVariable Long id) {
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());
		try {
			User user = loginService.getUserById(id);
			responseWrapper.setData(new ArrayList<UserToList>());
			assemblyResponseSuccessOperation(responseWrapper);

			if (user != null) {
				responseWrapper.setData(Collections.singletonList( modelMapper.map(user, UserToList.class)));
			} else {				
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.findById.notFound"));
				responseWrapper.setSuccess(false);
			}
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseWrapper<UserToList> create(@RequestBody UserToUpdate userInput) {

		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();

		try {

			Set<ConstraintViolation<UserToUpdate>> validate = beanValidator.validate(userInput);

			if (!validate.isEmpty()) {
				responseWrapper.setMessage(validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
				assemblyResponseToInvalidInput(responseWrapper);
			} else {

				User user = modelMapper.map(userInput, User.class);
				UserToList userDto = toUserToList(loginService.create(user));

				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setData(Arrays.asList(userDto));
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.create.success", user.getUsername()));
			}

		} catch (DataIntegrityViolationException e) {
			assemblyResponseToBadRequest(responseWrapper, e);

		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}

	@PutMapping
	@ResponseBody
	public ResponseWrapper<UserToList> change(@RequestBody UserToUpdate userInput) {
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();

		try {

			Set<ConstraintViolation<UserToUpdate>> validate = beanValidator.validate(userInput);

			if (!validate.isEmpty()) {
				responseWrapper.setMessage(validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
				assemblyResponseToInvalidInput(responseWrapper);
			} else {

				User user = modelMapper.map(userInput, User.class);
				UserToList userDto = toUserToList(loginService.change(user));

				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.change.success", user.getUsername()));
				responseWrapper.setData(Arrays.asList(userDto));
			}

		} catch (DataIntegrityViolationException e) {
			assemblyResponseToBadRequest(responseWrapper, e);

		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<UserToList> delete(@PathVariable Long id) {
		ResponseWrapper<UserToList> responseWrapper = new ResponseWrapper<UserToList>();
		responseWrapper.setData(new ArrayList<UserToList>());

		try {
			User user = loginService.delete(id);
			assemblyResponseSuccessOperation(responseWrapper);
			responseWrapper.setMessage(msgUtil.getMessageBundle("operation.user.delete.success", user.getUsername()));

		} catch (Throwable e) {

			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}

	private UserToList toUserToList(User user) {
		return modelMapper.map(user, UserToList.class);
	}

}
