package com.core.advice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.core.common.SystemConstant;
import com.core.exception.JsonViewException;
import com.core.exception.ModelViewException;

/**
 * <pre>
 * Class Name : ExceptionAdvice.
 * Description : 시스템 공통 예외 처리 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 31.
 * @version 1.0.0
 * @see
 */
@ControllerAdvice()
public class ExceptionAdvice {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * systemErrorPage.
	 */
	@Value("#{application['system.error.page']}")
	private String systemErrorPage;

	/**
	 * messageSource.
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * 기본 생성자.
	 */
	public ExceptionAdvice() {
		logger.debug("SessionAdvice Stand by");
	}

	/**
	 * JsonViewException 을 제어하는 메소드.
	 * 
	 * @param exception
	 *            JsonViewException
	 * @return Map<String, Object>
	 */
	@ExceptionHandler(JsonViewException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String, Object> handleJsonViewException(JsonViewException exception) {

		Map<String, Object> requesData = exception.getRequestData();

		String contentType = requesData.get(SystemConstant.CONTENT).toString();
		Locale locale = (Locale) requesData.get(SystemConstant.LOCALE);
		String errorCode = requesData.get(SystemConstant.ERROR_CODE).toString();
		String[] arg = (String[]) requesData.get(SystemConstant.MSM_ARG);

		logger.debug("handleJsonViewException contentType : {}", contentType);
		logger.debug("handleJsonViewException locale : {}", locale.toString());
		logger.debug("handleJsonViewException errorCode : {}", errorCode);
		logger.debug("handleJsonViewException message : {}", exception.getMessage());

		if (arg != null) {
			for (String value : arg) {
				logger.debug("handleJsonViewException arg : {}", value);
			}
		}

		return this.getJsonMessage(errorCode, exception.getMessage(), arg, locale);
	}

	/**
	 * ModelViewException 을 제어하는 메소드.
	 * 
	 * @param exception
	 *            ModelViewException
	 * @return ModelAndView
	 */
	@ExceptionHandler(ModelViewException.class)
	public ModelAndView handleModelViewException(ModelViewException exception) {

		ModelAndView result = null;
		Map<String, Object> requesData = exception.getRequestData();

		String contentType = requesData.get(SystemConstant.CONTENT).toString();
		Locale locale = (Locale) requesData.get(SystemConstant.LOCALE);
		String errorCode = requesData.get(SystemConstant.ERROR_CODE).toString();
		String[] arg = (String[]) requesData.get(SystemConstant.MSM_ARG);

		logger.debug("handleModelViewException errorCode : {}", errorCode);
		logger.debug("handleModelViewException contentType : {}", contentType);
		logger.debug("handleModelViewException locale : {}", locale.toString());
		logger.debug("handleModelViewException message : {}", exception.getMessage());

		if (arg != null) {
			for (String value : arg) {
				logger.debug("handleModelViewException arg : {}", value);
			}
		}

		result = this.getMessage();

		return result;
	}

	/**
	 * JsonMessage 를 반환하는 메소드.
	 * 
	 * @param errorCode
	 *            String
	 * @param errorMessage
	 *            String
	 * @param arg
	 *            String[]
	 * @param locale
	 *            Locale
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getJsonMessage(String errorCode, String errorMessage, String[] arg, Locale locale) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (errorMessage != null) {
			map.put(SystemConstant.ERROR_CODE, errorCode);
			map.put(SystemConstant.ERROR_MSG, messageSource.getMessage(errorMessage, arg, locale));
		} else {
			map.put(SystemConstant.ERROR_CODE, errorCode);
			map.put(SystemConstant.ERROR_MSG, messageSource.getMessage("msg_sys_error", arg, locale));
		}

		return map;
	}

	/**
	 * BaseMessage 를 반환하는 메소드.
	 * 
	 * @return ModelAndView
	 */
	private ModelAndView getMessage() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(systemErrorPage);

		return modelAndView;
	}

}