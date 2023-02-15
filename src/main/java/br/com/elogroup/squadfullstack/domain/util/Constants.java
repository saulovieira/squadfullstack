package br.com.elogroup.squadfullstack.domain.util;

import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;

public class Constants {

	public static final String DEFAULT_MESSAGE_MISSING_BUNDLE = "Message not found in the bundle";
	public static final Locale LOCALE_APP = LocaleContextHolder.getLocale();
}
