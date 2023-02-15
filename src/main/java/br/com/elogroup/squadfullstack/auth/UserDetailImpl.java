package br.com.elogroup.squadfullstack.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.elogroup.squadfullstack.model.User;


public class UserDetailImpl implements UserDetails {

	private static final long serialVersionUID = 5085659207802387500L;
	private final Optional<User> usuario;

    public UserDetailImpl(Optional<User> usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
    	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    	
    	if(usuario.isPresent()) {
    		usuario.get().getRoles().stream().forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getName())));
    	}
    	return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new User()).getEmail();
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
        return usuario.orElse(new User()).isEnabled();
    }
}