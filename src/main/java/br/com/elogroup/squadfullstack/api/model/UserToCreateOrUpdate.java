package br.com.elogroup.squadfullstack.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToCreateOrUpdate {
	
	private long id;
	
	private String username;
	
	private String password;
		
	private String email;
	
	private Boolean isEnabled;
	
}
