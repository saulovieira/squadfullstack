package br.com.elogroup.squadfullstack.api.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoubtToCreate implements InputModel {
	
	@NotBlank(message = "{constraints.question.NotEmpty}")
	private String question;
	
	@NotBlank(message = "{constraints.answer.NotEmpty}")
	private String answer;
	
	@NotNull(message = "{constraints.categoryId.NotNull}")
	private Long categoryId;
}
