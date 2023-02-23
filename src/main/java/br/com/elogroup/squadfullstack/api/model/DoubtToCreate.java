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
public class DoubtToCreate implements ResourceModel {
	
	@NotBlank(message = "{constraints.question.NotBlank}")
	private String question;
	
	@NotBlank(message = "{constraints.answer.NotBlank}")
	private String answer;
	
	@NotNull(message = "{constraints.categoryId.NotNull}")
	private Long categoryId;
}
