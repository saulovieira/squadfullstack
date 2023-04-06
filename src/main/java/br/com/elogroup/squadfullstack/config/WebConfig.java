package br.com.elogroup.squadfullstack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		/*
		* Essa configuração permite que as exceções de response sejam capturadas pelo exception handler implementado.
		* Em application.properties 
		* spring.mvc.throwExceptionIfNoHandlerFound=true
		* 
		* Ver discussão: https://stackoverflow.com/questions/54116245/404-exception-not-handled-in-spring-controlleradvice 
		*/
	}
} 