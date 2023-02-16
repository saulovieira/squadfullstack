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

import br.com.elogroup.squadfullstack.api.model.DoubtToCreate;
import br.com.elogroup.squadfullstack.api.model.DoubtToListAndUpdate;
import br.com.elogroup.squadfullstack.domain.model.Doubt;
import br.com.elogroup.squadfullstack.domain.sevice.DoubtService;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.ConstraintViolation;

@Controller
@RequestMapping("/api/doubt")
public class DoubtController extends BaseController {
	
	@Autowired
	private DoubtService doubtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LocalValidatorFactoryBean beanValidator;
	
	@Autowired
	private MessageUtil msgUtil;
	
	@GetMapping()
	@ResponseBody
	public ResponseWrapper<DoubtToListAndUpdate> getAll(){
		ResponseWrapper<DoubtToListAndUpdate> responseWrapper = new ResponseWrapper<DoubtToListAndUpdate>();
		responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());
		try {
			List<Doubt> users = doubtService.getDoubts();
			responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());
			assemblyResponseSuccessOperation(responseWrapper);
			
			if(users != null && !users.isEmpty()) {
				List<DoubtToListAndUpdate> usersDto = users
						.stream()
						.map(this::toDoubtDTO)
						.collect(Collectors.toList());
				
				responseWrapper.setData(usersDto);
				
			} else {
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.doubt.findAll.notFound"));
				responseWrapper.setSuccess(false);
			}
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);			
		}
		return responseWrapper;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<DoubtToListAndUpdate> getById(@PathVariable Long id) {
		ResponseWrapper<DoubtToListAndUpdate> responseWrapper = new ResponseWrapper<DoubtToListAndUpdate>();
		responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());
		try {
			Doubt doubt = doubtService.geDoubtById(id);
			responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());
			assemblyResponseSuccessOperation(responseWrapper);

			if (doubt != null) {
				responseWrapper.setData(Collections.singletonList( modelMapper.map(doubt, DoubtToListAndUpdate.class)));
			} else {				
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.doubt.findById.notFound"));
				responseWrapper.setSuccess(false);
			}
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);

		}
		return responseWrapper;
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseWrapper<DoubtToCreate> create(@RequestBody DoubtToCreate doubtInput){
		ResponseWrapper<DoubtToCreate> responseWrapper = new ResponseWrapper<DoubtToCreate>();
		responseWrapper.setData(new ArrayList<DoubtToCreate>());

		try {
			
			Set<ConstraintViolation<DoubtToCreate>> validate = beanValidator.validate(doubtInput);

			if (!validate.isEmpty()) {
				responseWrapper.setMessage(validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
				assemblyResponseToInvalidInput(responseWrapper);
			} else {
			
				Doubt user = modelMapper.map(doubtInput, Doubt.class);
				DoubtToListAndUpdate userDto = toDoubtDTO(doubtService.create(user));
				
				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setData(Arrays.asList(userDto));
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.doubt.create.success", user.getQuestion()));
			}
			
		} catch(DataIntegrityViolationException e) {
			assemblyResponseToBadRequest(responseWrapper, e);
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);		
			
		}
		return responseWrapper;
	}
	
	@PutMapping
	@ResponseBody
	public ResponseWrapper<DoubtToListAndUpdate> change(@RequestBody DoubtToListAndUpdate doubtInput){
		ResponseWrapper<DoubtToListAndUpdate> responseWrapper = new ResponseWrapper<DoubtToListAndUpdate>();
		responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());

		try {
			Set<ConstraintViolation<DoubtToCreate>> validate = beanValidator.validate(doubtInput);
			if (!validate.isEmpty()) {
				responseWrapper.setMessage(validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
				assemblyResponseToInvalidInput(responseWrapper);
			} else {
				Doubt user = modelMapper.map(doubtInput, Doubt.class);
				DoubtToListAndUpdate userDto = toDoubtDTO(doubtService.change(user));
				
				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setData(Arrays.asList(userDto));
				responseWrapper.setMessage(msgUtil.getMessageBundle("operation.doubt.change.success", user.getQuestion()));
			}
		} catch(DataIntegrityViolationException e) {
			assemblyResponseToBadRequest(responseWrapper, e);
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);		
			
		}
		return responseWrapper;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<DoubtToListAndUpdate> delete(@PathVariable Long id){
		ResponseWrapper<DoubtToListAndUpdate> responseWrapper = new ResponseWrapper<DoubtToListAndUpdate>();
		responseWrapper.setData(new ArrayList<DoubtToListAndUpdate>());

		try {			
			Doubt user = doubtService.delete(id);
			
			assemblyResponseSuccessOperation(responseWrapper);
			responseWrapper.setMessage(msgUtil.getMessageBundle("operation.doubt.delete.success", user.getQuestion()));
			
		} catch (Throwable e) {
			assembyResponseInternalServerError(responseWrapper, e);		
			
		}
		return responseWrapper;
	}
	
	
	private DoubtToListAndUpdate toDoubtDTO(Doubt user){
		return modelMapper.map(user, DoubtToListAndUpdate.class);
	}
	
}
