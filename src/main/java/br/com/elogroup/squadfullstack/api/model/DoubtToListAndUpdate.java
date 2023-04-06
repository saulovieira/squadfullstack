package br.com.elogroup.squadfullstack.api.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoubtToListAndUpdate extends DoubtToCreate {
	
	@NotNull(message = "{constraints.Id.NotNull}")
	private Long id;
	
}
