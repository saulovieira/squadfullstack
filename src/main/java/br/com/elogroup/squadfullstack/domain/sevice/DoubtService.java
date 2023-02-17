package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.domain.model.Doubt;
import br.com.elogroup.squadfullstack.domain.repository.DoubtRespository;
import br.com.elogroup.squadfullstack.util.CollectionUtil;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.Valid;

@Service
public class DoubtService {

	@Autowired
	private DoubtRespository repository;
	
	@Autowired
	private CollectionUtil<Doubt> doubtUtils;

	
	@Autowired
	private MessageUtil msgUtils;
	
	
	public List<Doubt> getDoubts() {

		return doubtUtils.getListFromIterable(repository.findAll());
	}

	public Doubt geDoubtById(Long id) {
		 
		return repository.findById(id).orElse(null);
	}
	
	public Doubt create(@Valid Doubt doubt) throws Exception {
		
		if (repository.findByQuestion(doubt.getQuestion()).isPresent()) {
			throw new DataIntegrityViolationException(msgUtils.getLocalizedMessage("exception.doubt.duplicateQuestion", doubt.getQuestion()));
		}
		
		return repository.save(doubt);
	}
	
	public Doubt change(@Valid Doubt doubt) throws Exception {
		
		if(doubt.getId() == null) {
			throw new DataIntegrityViolationException(msgUtils.getLocalizedMessage("constraints.id.NotEmpty", doubt.getId()));
			
		} else if (repository.findById(doubt.getId()).isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getLocalizedMessage("exception.doubt.notExists", doubt.getId()));
			
		} 
		
		return repository.save(doubt);			
	}
	
	public Doubt delete(Long id) throws Exception {
		
		if(id == null) {
			throw new DataIntegrityViolationException(msgUtils.getLocalizedMessage("constraints.id.NotEmpty", id));
			
		}
		
		Optional<Doubt> userToDelete = repository.findById(id);
		if (userToDelete.isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getLocalizedMessage("exception.doubt.notExists", id));
		} 
		
		repository.delete(userToDelete.get());	
		
		return userToDelete.get();

	}
}
