package br.com.elogroup.squadfullstack.util;

import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String DEFAULT_MESSAGE_MISSING_BUNDLE = "Message not found in the bundle : {%s}";
	public static final Locale LOCALE_APP = LocaleContextHolder.getLocale();
}
