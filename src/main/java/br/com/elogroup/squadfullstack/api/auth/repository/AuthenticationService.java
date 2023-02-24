package br.com.elogroup.squadfullstack.api.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.elogroup.squadfullstack.api.auth.model.AuthenticationRequest;
import br.com.elogroup.squadfullstack.api.auth.model.AuthenticationResponse;
import br.com.elogroup.squadfullstack.api.auth.service.JwtService;
import br.com.elogroup.squadfullstack.api.exception.BadRequestException;
import br.com.elogroup.squadfullstack.api.exception.ConflictException;
import br.com.elogroup.squadfullstack.api.exception.NotFoundException;
import br.com.elogroup.squadfullstack.api.model.UserRequest;
import br.com.elogroup.squadfullstack.api.model.UserResponse;
import br.com.elogroup.squadfullstack.domain.model.Role;
import br.com.elogroup.squadfullstack.domain.model.UserDetail;
import br.com.elogroup.squadfullstack.domain.repository.UserDetailRespository;
import br.com.elogroup.squadfullstack.util.CollectionUtil;
import br.com.elogroup.squadfullstack.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserDetailRespository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final MessageUtil msgUtil;
	private final CollectionUtil<UserDetail> collectionUtil;

	public AuthenticationResponse register(UserRequest request) {

		if (repository.findByEmail(request.getEmail()).isPresent()) {
			throw new ConflictException(
					msgUtil.getLocalizedMessage("exception.user.duplicateEmail", request.getEmail()));
		}
		
		var user = UserDetail.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		
		repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
		.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), 
						request.getPassword()
					)
			);
		var user = repository.findByEmail(request.getEmail());
		if (user.isEmpty()) {
			throw new BadRequestException(
					msgUtil.getLocalizedMessage("exception.user.badcredentials", request.getEmail()));
		}
		var jwtToken = jwtService.generateToken(user.get());
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public List<UserDetail> getAll() {
		
		return collectionUtil.getListFromIterable(repository.findAll());
	}

	public UserDetail getById(Long id) {
		
		Optional<UserDetail> userDetail = repository.findById(id);
		if (!userDetail.isPresent()) {
			throw new NotFoundException(
					msgUtil.getLocalizedMessage("exception.user.notExists",  id));
		}
		return userDetail.get();
	}

	public UserDetail update(UserResponse user) {
		Optional<UserDetail> userDetail = repository.findByEmail(user.getEmail());
		if (!userDetail.isPresent()) {
			throw new NotFoundException(
					msgUtil.getLocalizedMessage("exception.user.notExists",  user.getEmail()));
		}
		return repository.findByEmail(user.getEmail()).orElse(null);
	}

	public UserDetail delete(Long id) {
		
		UserDetail user = getById(id);
		repository.delete(user);
		return user;
	}
}