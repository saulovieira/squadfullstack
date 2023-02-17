package br.com.elogroup.squadfullstack.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.elogroup.squadfullstack.domain.model.UserDetail;

public interface UserDetailRespository extends CrudRepository<UserDetail, Long> {
	
	Optional<UserDetail> findByEmail(String email);

}
