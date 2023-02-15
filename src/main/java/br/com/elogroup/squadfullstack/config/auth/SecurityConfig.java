package br.com.elogroup.squadfullstack.config.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.elogroup.squadfullstack.domain.model.Role;
import br.com.elogroup.squadfullstack.domain.model.User;
import br.com.elogroup.squadfullstack.repository.UserRespository;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
 	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Autowired
			private UserRespository userRespository;
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				Optional<User> userToCheck = userRespository.findByUsername(username);
				
				User user = null;
				List<Role> roles = new ArrayList<>();
				roles.add(new Role("USER"));
				roles.add(new Role("ADMIN"));
				
				
				if(userToCheck.isPresent()) {
					user = userToCheck.get();
					user.setRoles(roles);
					
				} else if(username != null && username.equals("admin")) {

					user = User.builder()
			 	 			.username("admin")
			 	 			.password(passwordEncoder().encode("53cr3t"))
			 	 			.roles(roles)
			 	 			.isEnabled(true)
			 	 			.build();
				} else {
					throw new UsernameNotFoundException("Colud not find user: " + username);
				}
				return new UserDetailImpl(Optional.of(user));
			}
		};
		
	}
	
	
 	@Bean 
 	DaoAuthenticationProvider authProvider() {
 	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 	    authProvider.setUserDetailsService(userDetailsService());
 	    authProvider.setPasswordEncoder(passwordEncoder());
 	    return authProvider;
 	}
 	
	
 	@Bean
 	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 		 http.csrf().disable()
	 		 .authorizeHttpRequests()
		 		 .requestMatchers("/api/user")
		 		 	.authenticated()
			 	 .requestMatchers("/api/user/*")
			 		 	.authenticated()
		 		 .and()
		 		 		.httpBasic();
         return http.build();
 	}
}
