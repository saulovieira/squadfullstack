package br.com.elogroup.squadfullstack.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.model.User;
import br.com.elogroup.squadfullstack.repository.UserRespository;
import br.com.elogroup.squadfullstack.utils.ServiceUtils;
import jakarta.validation.Valid;

@Service
public class LoginService {

	@Autowired
	private UserRespository userRespository;
	
	@Autowired
	private ServiceUtils<User> userUtils;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public List<User> getUsers() {

		return userUtils.getListFromIterable(userRespository.findAll());
	}

	public User create(@Valid User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRespository.save(user);
	}
}
