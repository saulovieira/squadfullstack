package br.com.elogroup.squadfullstack.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	@NotBlank(message = "{constraints.firstname.NotBlank}")
	private String firstname;
	
	@NotBlank(message = "{constraints.lastname.NotBlank}")
	private String lastname;
	
	@NotBlank(message = "{constraints.email.NotBlank}")
	private String email;
	
	@NotBlank(message = "{constraints.password.NotBlank}")
	private String password;
}