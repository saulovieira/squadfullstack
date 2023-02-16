package br.com.elogroup.squadfullstack.domain.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private String email;
	
	private String username;
	
	private String password;
	
	private Boolean isEnabled;

	@ManyToMany
	private List<Role> roles;
}
