package br.com.elogroup.squadfullstack.domain.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
@Entity
public class Doubt {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	@Id
	private Long Id;
	
	@NotBlank(message = "{constraints.question.NotEmpty}")
	private String question;
	
	@NotBlank(message = "{constraints.answer.NotEmpty}")
	private String answer;
	
	@Nonnull
	@ManyToOne()
	private Category category;
}
