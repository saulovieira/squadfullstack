package br.com.elogroup.squadfullstack.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryToCreate implements resourceModel {
	
	@NotBlank(message = "{constraints.name.NotBlank}")
	private String name;
	
}
