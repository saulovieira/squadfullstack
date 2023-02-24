package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.api.exception.ConflictException;
import br.com.elogroup.squadfullstack.api.exception.NotFoundException;
import br.com.elogroup.squadfullstack.domain.model.Category;
import br.com.elogroup.squadfullstack.domain.repository.CategoryRepository;
import br.com.elogroup.squadfullstack.util.CollectionUtil;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private CollectionUtil<Category> userUtils;
	
	@Autowired
	private MessageUtil msgUtils;
	
	public List<Category> getCategories() {

		return userUtils.getListFromIterable(repository.findAll());
	}

	public Category create(@Valid Category category) throws Exception {
		
		if (repository.findByName(category.getName()).isPresent()) {
			throw new ConflictException(msgUtils.getLocalizedMessage("exception.category.duplicateName", category.getName()));
		}

		return repository.save(category);
	}
	
	public Category change(@Valid Category doubt) throws Exception {
		
		if (repository.findById(doubt.getId()).isEmpty()) {
			throw new NotFoundException(msgUtils.getLocalizedMessage("exception.category.notExists", doubt.getId()));
			
		} 
		
		return repository.save(doubt);			
	}
	
	public Category delete(Long id) throws Exception {
			
		Optional<Category> userToDelete = repository.findById(id);
		if (userToDelete.isEmpty()) {
			throw new NotFoundException(msgUtils.getLocalizedMessage("exception.category.notExists", id));
		} 
		
		repository.delete(userToDelete.get());	
		
		return userToDelete.get();

	}
}
