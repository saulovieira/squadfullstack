package br.com.elogroup.squadfullstack.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.elogroup.squadfullstack.model.User;

public interface UserRespository extends CrudRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
