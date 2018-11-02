package com.core.message;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

/**
 * <pre>
 * Class Name : ExposedMessageResource.
 * Description : 메시지 리소스를 추출하기 위한 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 4. 7.
 * @version 1.0.0
 * @see
 */
public class ExposedMessageResource extends ReloadableResourceBundleMessageSource {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Properties loadProperties(Resource resource, String fileName) throws IOException {

		logger.info("Load " + fileName);
		return super.loadProperties(resource, fileName);
	}

	/**
	 * Gets all messages for presented Locale.
	 * 
	 * @param locale
	 *            user request's locale
	 * @return all messages
	 */
	public Properties getMessages(Locale locale) {
		return getMergedProperties(locale).getProperties();
	}
}