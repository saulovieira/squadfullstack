package br.com.elogroup.squadfullstack.domain.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.domain.model.User;
import br.com.elogroup.squadfullstack.domain.util.CollectionUtil;
import br.com.elogroup.squadfullstack.repository.UserRespository;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.validation.Valid;

@Service
public class LoginService {

	@Autowired
	private UserRespository repository;
	
	@Autowired
	private MessageUtil msgUtils;
	
	@Autowired
	private CollectionUtil<User> collectionUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	

	public User getUserById(Long id) {

		return repository.findById(id).orElse(null);
	}
	
	public List<User> getUsers() {

		return collectionUtil.getListFromIterable(repository.findAll());
	}

	public User create(@Valid User user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		
		if (repository.findByEmail(user.getEmail()).isPresent()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.user.duplicateEmail", user.getEmail()));
		}

		return repository.save(user);

	}
	
	public User change(@Valid User user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		
		if(user.getId() == null) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("constraints.id.NotEmpty", user.getId()));
			
		} else if (repository.findByEmail(user.getEmail()).isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.user.notExists", user.getId()));
			
		} 
		
		return repository.save(user);			

	}
	
	public User delete(Long id) throws Exception {
		
		if(id == null) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("constraints.id.NotEmpty", id));	
		}
		
		Optional<User> userToDelete = repository.findById(id);
		if (userToDelete.isEmpty()) {
			throw new DataIntegrityViolationException(msgUtils.getMessageBundle("exception.user.notExists", id));
		} 
		
		repository.delete(userToDelete.get());	
		
		return userToDelete.get();

	}
}
