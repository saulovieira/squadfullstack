package br.com.elogroup.squadfullstack.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryToListAndUpdate extends CategoryToCreate {

	@NotNull(message = "{constraints.id.NotNull}")
	private Long id;
}
