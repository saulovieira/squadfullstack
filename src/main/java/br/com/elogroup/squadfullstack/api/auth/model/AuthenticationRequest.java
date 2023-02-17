package br.com.elogroup.squadfullstack.api.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "{constraints.email.NotBlank}")
	private String email;

	@NotBlank(message = "{constraints.password.NotBlank}")
	private String password;
}