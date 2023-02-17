package br.com.elogroup.squadfullstack.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import br.com.elogroup.squadfullstack.api.exception.ForbiddenException;
import br.com.elogroup.squadfullstack.api.exception.InternalServerErrorException;
import br.com.elogroup.squadfullstack.api.model.CategoryToCreate;
import br.com.elogroup.squadfullstack.api.model.CategoryToListAndUpdate;
import br.com.elogroup.squadfullstack.domain.model.Category;
import br.com.elogroup.squadfullstack.domain.sevice.CategoryService;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
	
	private final CategoryService categoryService;
	private final ModelMapper modelMapper;
	private final LocalValidatorFactoryBean beanValidator;
	private final MessageUtil msgUtil;
	
	@GetMapping()
	@ResponseBody
	public ResponseWrapper<CategoryToCreate> getAll(){
		ResponseWrapper<CategoryToCreate> responseWrapper = new ResponseWrapper<CategoryToCreate>();
		responseWrapper.setData(new ArrayList<CategoryToCreate>());
		try {
			List<Category> users = categoryService.getCategories();
			responseWrapper.setData(new ArrayList<CategoryToCreate>());
			assemblyResponseSuccessOperation(responseWrapper);
			
			if(users != null && !users.isEmpty()) {
				
				List<CategoryToCreate> usersDto = users
						.stream()
						.map(this::toCategoryToList)
						.collect(Collectors.toList());
				
				responseWrapper.setData(usersDto);
			} else {
				responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.category.findAll.notFound"));
				responseWrapper.setSuccess(false);
			}
			
		} catch (ForbiddenException | DataIntegrityViolationException e) {
			throw new ForbiddenException(e.getMessage());
		} catch (Throwable e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		return responseWrapper;
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseWrapper<CategoryToCreate> create(@RequestBody CategoryToCreate categoryInput){
		ResponseWrapper<CategoryToCreate> responseWrapper = new ResponseWrapper<CategoryToCreate>();
		responseWrapper.setData(new ArrayList<CategoryToCreate>());

		try {			
			Set<ConstraintViolation<@Valid CategoryToCreate>> validate = beanValidator.validate(categoryInput);
			if (!validate.isEmpty()) {
				throw new ForbiddenException(validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
			} else {
			
				Category user = modelMapper.map(categoryInput, Category.class);
				CategoryToCreate userDto = toCategoryToList(categoryService.create(user));
	
				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setData(Arrays.asList(userDto));
				responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.category.create.success", user.getName()));
			}
			
		}  catch (ForbiddenException | DataIntegrityViolationException e) {
			throw new ForbiddenException(e.getMessage());
		} catch (Throwable e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		return responseWrapper;
	}

	@PutMapping
	@ResponseBody
	public ResponseWrapper<CategoryToCreate> change(@RequestBody CategoryToListAndUpdate categoryInput){
		ResponseWrapper<CategoryToCreate> responseWrapper = new ResponseWrapper<CategoryToCreate>();
		responseWrapper.setData(new ArrayList<CategoryToCreate>());

		try {			
			Set<ConstraintViolation<CategoryToListAndUpdate>> validate = beanValidator.validate(categoryInput);
			if (!validate.isEmpty()) {
				throw new ForbiddenException (validate.stream()
						.map(mess -> String.format("'%s'", mess.getMessage().toString()))
						.collect(Collectors.toList()).toString());
			} else {
			
				Category user = modelMapper.map(categoryInput, Category.class);
				CategoryToListAndUpdate userDto = toCategoryToList(categoryService.change(user));
				
				assemblyResponseSuccessOperation(responseWrapper);
				responseWrapper.setData(Arrays.asList(userDto));
				responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.category.change.success", user.getName()));
			}
		} catch (ForbiddenException | DataIntegrityViolationException e) {
			throw new ForbiddenException(e.getMessage());
		} catch (Throwable e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		return responseWrapper;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseWrapper<CategoryToCreate> delete(@PathVariable Long id){
		ResponseWrapper<CategoryToCreate> responseWrapper = new ResponseWrapper<CategoryToCreate>();
		responseWrapper.setData(new ArrayList<CategoryToCreate>());

		try {			
			assemblyResponseSuccessOperation(responseWrapper);			
			Category user = categoryService.delete(id);
			responseWrapper.setMessage(msgUtil.getLocalizedMessage("operation.category.delete.success", user.getName()));
			
		} catch (ForbiddenException | DataIntegrityViolationException e) {
			throw new ForbiddenException(e.getMessage());
		} catch (Throwable e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		return responseWrapper;
	}
	
	
	private CategoryToListAndUpdate toCategoryToList(Category user){
		return modelMapper.map(user, CategoryToListAndUpdate.class);
	}
	
}
