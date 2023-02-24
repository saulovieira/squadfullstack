package br.com.elogroup.squadfullstack.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import br.com.elogroup.squadfullstack.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class MessageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	private List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("pt"));

	@Bean
	MessageSource messageSource() {

		ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
		rs.setBasename("messages");
		rs.setDefaultEncoding("UTF-8");
		rs.setUseCodeAsDefaultMessage(true);
		return rs;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader("Accept-Language");
		return headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
	}
	
	@Bean
	MessageUtil messageUtil() {
		return new MessageUtil();
	}

}
