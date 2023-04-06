package br.com.elogroup.squadfullstack.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		//Add custom strategies to convert fields
		
		return new ModelMapper();
	}
}
