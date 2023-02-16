package br.com.elogroup.squadfullstack.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryToCreate implements InputModel {
	
	@NotBlank(message = "{constraints.name.NotEmpty}")
	private String name;
	
}
