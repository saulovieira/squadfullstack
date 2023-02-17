package br.com.elogroup.squadfullstack.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.elogroup.squadfullstack.domain.model.Doubt;

public interface DoubtRespository extends CrudRepository<Doubt, Long> {
	
	Optional<Doubt> findByQuestion(String question);
	
	Optional<Doubt> findByAnswer(String answer);

}
