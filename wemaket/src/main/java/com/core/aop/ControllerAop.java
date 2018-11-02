package com.core.aop;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.core.common.RequestArgsVO;
import com.core.common.SystemConstant;
import com.core.exception.BizException;
import com.core.exception.CommonException;
import com.core.exception.JsonViewException;
import com.core.exception.ModelViewException;

/**
 * <pre>
 * Class Name : ControllerAop.
 * Description : Controller 계층의 로그와 예외 처리를 위한 Aop 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 31.
 * @version 1.0.0
 * @see
 */
@Aspect
public class ControllerAop {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 기본 생성자.
	 */
	public ControllerAop() {
		logger.debug("ControllerAop Stand by");
	}

	/**
	 * Controller 메소드에 around 처리를 한다.
	 * 
	 * @param joinPoint
	 *            ProceedingJoinPoint
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object around(ProceedingJoinPoint joinPoint) {

		Object[] args = joinPoint.getArgs();
		Object returnValue = null;

		String targetName = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		long startTime = 0;
		long endTime = 0;

		logger.debug("==========>>>>>>>>>> {} ", targetName + "." + methodName + " Call");

		RequestArgsVO vo = this.getArgs(args);
		String contentType = vo.getRequestData().get(SystemConstant.CONTENT).toString();
 
		// Session 의 userData 를 Map 에 추가한다.
		if (vo.getParamData() != null) {
			this.setUserData(vo.getParamData(), vo.getRequestData());
		}

		startTime = System.currentTimeMillis();
		 
		try {

			returnValue = joinPoint.proceed();
			
			if (returnValue instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) returnValue;
				map.put(SystemConstant.ERROR_CODE, "0");
				map.put(SystemConstant.ERROR_MSG, "");
				logger.debug("==========>>>>>>>>>> return : {}", map.toString());
			} else if (returnValue instanceof ModelAndView) {
				ModelAndView modelAndView = (ModelAndView) returnValue;
				logger.debug("==========>>>>>>>>>> return : {}", modelAndView.toString());
			} else {
				if(returnValue != null) {
					logger.debug("==========>>>>>>>>>> return : {}", returnValue.toString());
				}
			}

		} catch (Throwable e) {

			if (e instanceof BizException || e instanceof CommonException) {
				// BizException, CommonException 은 로그처리 안함
				this.createException(e, vo.getRequestData(), contentType);
			} else {
				logger.error("Exception : ", e);
				this.createException(e, vo.getRequestData(), contentType);
			}

		}

		endTime = System.currentTimeMillis();

		logger.debug("==========>>>>>>>>>> {}.{} Executed Time : {} Millis", targetName, methodName, this.getExecutedTime(startTime, endTime));

		return returnValue;
	}

	/**
	 * 처리 소요시간을 계산해 주는 메소드.
	 * 
	 * @author kimhoil
	 * @since 2017. 3. 31.
	 * @param startTime
	 *            long
	 * @param endTime
	 *            long
	 * @return long
	 */
	private long getExecutedTime(long startTime, long endTime) {
		long result = 0;
		if (startTime != 0 && endTime != 0 && startTime < endTime) {
			result = endTime - startTime;
		}
		return result;
	}

	/**
	 * request 로 부터 입력된 인자값을 분리하여 vo 객체로 반환하는 메소드.
	 * 
	 * @param args
	 *            Object[]
	 * @return RequestArgsVO
	 */
	@SuppressWarnings("unchecked")
	private RequestArgsVO getArgs(Object[] args) {

		RequestArgsVO result = new RequestArgsVO();

		for (Object object : args) {

			if (object instanceof Map) {
				result.setParamData((Map<String, Object>) object);
				logger.debug("==========>>>>>>>>>> param : {} ", result.getParamData().toString());
			}

			if (object instanceof HttpServletRequest) {
				// HttpServletRequest 에서 사용 할 정보를 분리한다.
				result.setRequestData(this.getReqeustData((HttpServletRequest) object));
			}
		}

		return result;
	}

	/**
	 * joinPoint 에서 발생한 Exception을 처리하는 메소드.
	 * 
	 * @param e
	 *            Throwable
	 * @param requestData
	 *            Map<String,Object>
	 * @param contentType
	 *            String
	 * @throws ModelViewException
	 *             ModelView 로 처리하기 위한 예외
	 * @throws JsonViewException
	 *             JsonView 로 처리하기 위한 예외
	 */
	private void createException(Throwable e, Map<String, Object> requestData, String contentType) throws ModelViewException, JsonViewException {

		if (e instanceof BizException) {
			requestData.put(SystemConstant.MSM_ARG, ((BizException) e).getArg());
		}

		if (SystemConstant.JSON_CONTENT_TYPE.equals(contentType)) {

			// contentType 이 json 인경우
			throw new JsonViewException(e.getMessage(), requestData);
		} else {
			// contentType 이 ModelAndView 인경우
			throw new ModelViewException(e.getMessage(), requestData);
		}
	}

	/**
	 * reqeust 에 컨텐트 타입, 로케일, 사용자 정보를 분리하여 Map<String, Object>로 반환하는 메소드.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getReqeustData(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Map<String, Object> requestData = new HashMap<String, Object>();

		String contentType = request.getContentType();
		Locale locale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		
		if (contentType == null) {
			contentType = "";
		} else {
			contentType = contentType.split(";")[0].toLowerCase();
		}

		if (locale == null) {
			locale = request.getLocale();
		}
		
		Map<String, Object> HeaderInfo = new HashMap<String, Object>();
		
        HeaderInfo.put("Content-Type"   , request.getHeader("Content-Type"));
        HeaderInfo.put("TRANSACTION_ID_", request.getHeader("TRANSACTION_ID_"));
        HeaderInfo.put("DEVICE_TYPE"    , request.getHeader("DEVICE_TYPE"));
        HeaderInfo.put("DEVICE_ID"      , request.getHeader("DEVICE_ID"));
        HeaderInfo.put("APP_VERSION"    , request.getHeader("APP_VERSION"));
        HeaderInfo.put("SESSION_ID"     , request.getHeader("SESSION_ID"));
		
		requestData.put(SystemConstant.ERROR_CODE, SystemConstant.ERROR_DEFAULT);
		requestData.put(SystemConstant.CONTENT, contentType);
		requestData.put(SystemConstant.LOCALE, locale);
		//requestData.put(SystemConstant.USER_DATA, session.getAttribute(SystemConstant.USER_DATA));
		requestData.put(SystemConstant.HEADER_INFO, HeaderInfo);
		
        
		return requestData;
	}

	/**
	 * 입력된 paramData 에 사용자 정보를 추가하는 메소드.
	 * 
	 * @param paramData
	 *            Map<String, Object>
	 * @param requestData
	 *            Map<String, Object>
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> setUserData(Map<String, Object> paramData, Map<String, Object> requestData) {
		Map<String, Object> result = paramData;
		//Map<String, Object> userData = (Map<String, Object>) requestData.get(SystemConstant.USER_DATA);
		Map<String, Object> HeaderInfoData = (Map<String, Object>) requestData.get(SystemConstant.HEADER_INFO);
		
		if (HeaderInfoData != null) {
			result.putAll(HeaderInfoData);
		}
		return result;
	}

}