package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.domain.model.Category;
import br.com.elogroup.squadfullstack.domain.util.CollectionUtil;
import br.com.elogroup.squadfullstack.repository.CategoryRespository;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRespository repository;
	
	@Autowired
	private CollectionUtil<Category> userUtils;
	
	@Autowired
	private MessageUtil msgUtils;
	
	public List<Category> getCategories() {

		return userUtils.getListFromIterable(repository.findAll());
	}

	public Category create(@Valid Category category) throws Exception {
		
		if (repository.findByName(category.getName()).isPresent()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.category.duplicateName", category.getName()));
		}

		return repository.save(category);
	}
	
	public Category change(@Valid Category doubt) throws Exception {
		
		if(doubt.getId() == null) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("constraints.id.NotEmpty", doubt.getId()));
			
		} else if (repository.findById(doubt.getId()).isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.category.notExists", doubt.getId()));
			
		} 
		
		return repository.save(doubt);			
	}
	
	public Category delete(Long id) throws Exception {
		
		if(id == null) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("constraints.id.NotEmpty", id));
			
		}
		
		Optional<Category> userToDelete = repository.findById(id);
		if (userToDelete.isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.category.notExists", id));
		} 
		
		repository.delete(userToDelete.get());	
		
		return userToDelete.get();

	}
}
