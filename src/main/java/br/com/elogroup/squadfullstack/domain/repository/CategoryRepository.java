package br.com.elogroup.squadfullstack.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.elogroup.squadfullstack.domain.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	Optional<Category> findByName(String name);

}
