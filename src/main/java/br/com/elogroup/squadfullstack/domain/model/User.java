package br.com.elogroup.squadfullstack.domain.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ACCESS_USER")
@Builder	
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	@Id
	private Long Id;
	
	@Column(unique = true)
	@NotEmpty(message = "{constraints.email.NotEmpty}")
	private String email;
	
	@NotBlank(message = "{constraints.username.NotBlank}")
	private String username;
	
	@NotBlank(message = "{constraints.password.NotBlank}")
	private String password;
	
	@NotNull(message = "{constraints.isEnabled.NotNull}")
	private Boolean isEnabled;

	@ManyToMany
	private List<Role> roles;
}
