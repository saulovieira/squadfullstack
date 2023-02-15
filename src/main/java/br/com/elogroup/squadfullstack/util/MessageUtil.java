package br.com.elogroup.squadfullstack.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.com.elogroup.squadfullstack.domain.util.Constants;

@Component
public class MessageUtil {

	@Autowired
	private MessageSource messageSource;
	
	public String getMessageBundle(String messageKey) {
		return messageSource.getMessage(messageKey, null, Constants.DEFAULT_MESSAGE_MISSING_BUNDLE,  Constants.LOCALE_APP);
	}
	
	public String getMessageBundle(String messageKey, Object... args) {
		return String.format(messageSource.getMessage(messageKey, null, Constants.DEFAULT_MESSAGE_MISSING_BUNDLE,  Constants.LOCALE_APP), args) ;
	}
}
