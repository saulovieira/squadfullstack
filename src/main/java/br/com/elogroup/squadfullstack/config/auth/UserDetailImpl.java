package br.com.elogroup.squadfullstack.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.elogroup.squadfullstack.domain.model.User;


public class UserDetailImpl implements UserDetails {

	private static final long serialVersionUID = 5085659207802387500L;
	private final Optional<User> user;

    public UserDetailImpl(Optional<User> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
    	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    	
    	if(user.isPresent()) {
    		user.get().getRoles().stream()
    			.forEach(
    						reole -> authorities.add(new SimpleGrantedAuthority(reole.getName()))
    					);
    	}
    	return authorities;
    }

    @Override
    public String getPassword() {
        return user.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new User()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.orElse(new User()).getIsEnabled();
    }
}