package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.domain.model.Category;
import br.com.elogroup.squadfullstack.domain.util.CollectionUtil;
import br.com.elogroup.squadfullstack.repository.CategoryRespository;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRespository categoryRespository;
	
	@Autowired
	private CollectionUtil<Category> userUtils;
	
	public List<Category> getCategories() {

		return userUtils.getListFromIterable(categoryRespository.findAll());
	}

	public Category create(@Valid Category category) throws Exception {
		
		if (categoryRespository.findByName(category.getName()).isPresent()) {
			throw new DuplicateKeyException(String.format("Already exists a category with the name '%s' in database", category.getName()));
		}
		return categoryRespository.save(category);
	}
}
