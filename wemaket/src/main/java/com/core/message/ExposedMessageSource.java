package com.core.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <pre>
 * Class Name : ExposedMessageSource.
 * Description : 전체 메시지 소스를 추출하기 위한 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 4. 7.
 * @version 1.0.0
 * @see
 */
public class ExposedMessageSource implements MessageSourceAware {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * MessageSource msgSource.
	 */
	private MessageSource msgSource;

	/**
	 * 생성자.
	 */
	public ExposedMessageSource() {
		logger.debug("MessageResolverService Stand by");
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		logger.info("Messages i18n injected");
		this.msgSource = messageSource;
	}

	/**
	 * 메시지를 가지고 오는 메소드.
	 * 
	 * @param key
	 *            String
	 * @param arguments
	 *            Object[]
	 * @param locale
	 *            Locale
	 * @return String
	 */
	public String getMessage(String key, Object[] arguments, Locale locale) {
		String message = "";
		try {
			message = msgSource.getMessage(key, arguments, locale);
		} catch (NoSuchMessageException e) {
			message = key;
			logger.warn("No message found: " + key);
		}
		return message;
	}

	/**
	 * 로케일에 해당하는 전체 메시지를 Map<String, String> 으로 반환하는 메소드.
	 * 
	 * @param locale
	 *            Locale
	 * @return Map<String, String>
	 */
	public Map<String, String> getMessages(Locale locale) {
		Properties properties = ((XmlWebApplicationContext) msgSource).getBean("messageSource", ExposedMessageResource.class).getMessages(locale);
		Map<String, String> messagesMap = new HashMap<String, String>();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null) {
				messagesMap.put(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		return messagesMap;
	}
}