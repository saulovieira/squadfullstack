package br.com.elogroup.squadfullstack.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.elogroup.squadfullstack.model.User;
import br.com.elogroup.squadfullstack.sevice.LoginService;

@Controller
@RequestMapping("/api/user")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping()
	@ResponseBody
	public ResponseWrapper<User> getAll(){
		ResponseWrapper<User> responseWrapper = new ResponseWrapper<User>();
		responseWrapper.setData(new ArrayList<User>());
		try {
			List<User> users = loginService.getUsers();
			responseWrapper.setData(new ArrayList<User>());
			if(users != null) {
				responseWrapper.setData(users);
			}
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			if(users.isEmpty()) {
				responseWrapper.setMessage("No Users Found");
			}
		} catch (Exception e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
		}
		return responseWrapper;
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseWrapper<User> create(@Valid @RequestBody User user){
		ResponseWrapper<User> responseWrapper = new ResponseWrapper<User>();
		responseWrapper.setData(new ArrayList<User>());
		try {
			User userDto = loginService.create(user);
			responseWrapper.setData(Arrays.asList(userDto));
			responseWrapper.setStatus(HttpStatus.OK);
			responseWrapper.setStatusCode(HttpStatus.OK.value());
			responseWrapper.setMessage("Created user susessfull");
		} catch (Exception e) {
			responseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			responseWrapper.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setDescription(e.toString());
		}
		return responseWrapper;
	}
}
