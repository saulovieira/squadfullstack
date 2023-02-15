package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.domain.model.Doubt;
import br.com.elogroup.squadfullstack.domain.util.CollectionUtil;
import br.com.elogroup.squadfullstack.repository.DoubtRespository;
import jakarta.validation.Valid;

@Service
public class DoubtService {

	@Autowired
	private DoubtRespository doubtRespository;
	
	@Autowired
	private CollectionUtil<Doubt> doubtUtils;

	
	public List<Doubt> getDoubts() {

		return doubtUtils.getListFromIterable(doubtRespository.findAll());
	}

	public Doubt create(@Valid Doubt doubt) throws Exception {
		
		
		if (doubt.getCategory() == null || doubt.getCategory().getId() == null) {
			throw new DuplicateKeyException(String.format("Already exists a doubt with this question '%s' in database", doubt.getQuestion()));
		}
		
		if (doubtRespository.findByQuestion(doubt.getQuestion()).isPresent()) {
			throw new DuplicateKeyException(String.format("Already exists a doubt with this question '%s' in database", doubt.getQuestion()));
		}
		
		return doubtRespository.save(doubt);
	}
}
