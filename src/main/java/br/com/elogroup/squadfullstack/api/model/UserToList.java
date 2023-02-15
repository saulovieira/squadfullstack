package br.com.elogroup.squadfullstack.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToList {
	
	private long id;
	
	private String username;
		
	private String email;
	
	private Boolean isEnabled;
	
	private List<String> roles;
	
}
