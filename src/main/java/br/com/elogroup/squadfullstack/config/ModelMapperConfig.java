package br.com.elogroup.squadfullstack.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//configUser(modelMapper);
		
		return modelMapper;
	}
	
	
	@SuppressWarnings("unused")
	private void configUser(ModelMapper modelMapper) {
		
//		Converter<List<Role>, List<String>> rolesConverter = 
//				ctx -> ctx.getSource().stream().map(role -> role.getName()).toList(); 
//				
//		modelMapper.createTypeMap(User.class, UserToList.class)
//			.addMappings(mapper -> mapper.using(rolesConverter)
//			.map(User::getRoles, UserToList::setRoles));
	}
}
