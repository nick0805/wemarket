package com.core.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.core.common.SystemConstant;
import com.core.exception.BizException;
import com.core.exception.JsonViewException;
import com.core.exception.ModelViewException;

/**
 * <pre>
 * Class Name : SessionInterceptor.
 * Description : 세션 처리를 위한 인터셉터 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 31.
 * @version 1.0.0
 * @see
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 기본 생성자.
	 */
	public SessionInterceptor() {
		logger.debug("SessionInterceptor Stand by");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
		String uri = request.getRequestURI();
		logger.debug(uri);
		
		return true;
	}
	
	void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
		Enumeration enumeration =  request.getHeaderNames();
	    String sessionId = "";
	    while(enumeration.hasMoreElements()){
	    	String headerName = (String) enumeration.nextElement();
	    	//String headerValue = request.getHeader(headerName);
	    	sessionId = request.getHeader("session_id");
	    	//logger.debug("headerName : " + headerName);
	    	//logger.debug("headerValue : " + headerValue);
	    }
	    
	    mv.addObject("session_id", sessionId);
	    
	}
	
	private void ExceptionRise(Map<String, Object>requestData,String contentType){
		if (SystemConstant.JSON_CONTENT_TYPE.equals(contentType)) {
			throw new JsonViewException(requestData);
		} else {
			throw new ModelViewException(requestData);
		}
	}
}