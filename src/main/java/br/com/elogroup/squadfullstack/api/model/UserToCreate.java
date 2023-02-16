package br.com.elogroup.squadfullstack.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToCreate implements InputModel {
	
	@NotBlank(message = "{constraints.username.NotBlank}")
	private String username;
	
	@NotBlank(message = "{constraints.password.NotBlank}")
	private String password;
		
	@NotBlank(message = "{constraints.email.NotBlank}")
	private String email;
	
	@NotNull(message = "{constraints.isEnabled.NotNull}")
	private Boolean isEnabled;
	
	
}
