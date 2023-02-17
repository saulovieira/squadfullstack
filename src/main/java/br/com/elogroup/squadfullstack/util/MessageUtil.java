package br.com.elogroup.squadfullstack.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtil {

	@Autowired
	private MessageSource messageSource;

	public String getLocalizedMessage(String messageKey) {
		return messageSource.getMessage(messageKey, null, String.format(Constants.DEFAULT_MESSAGE_MISSING_BUNDLE, messageKey), getLocale());
	}

	public String getLocalizedMessage(String messageKey, Object... args) {
		return String.format(
				messageSource.getMessage(messageKey, null, String.format(Constants.DEFAULT_MESSAGE_MISSING_BUNDLE, messageKey), getLocale()),
				args);
	}

	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
