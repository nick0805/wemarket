package com.core.aop;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.exception.BizException;
import com.core.exception.CommonException;

/**
 * <pre>
 * Class Name : ServiceAop.
 * Description : Service 계층의 로그와 예외 처리를 위한 Aop 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 31.
 * @version 1.0.0
 * @see
 */
@Aspect
public class ServiceAop {

	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 기본 생성자.
	 */
	public ServiceAop() {
		logger.debug("ServiceAop Stand by");
	}

	/**
	 * Service 메소드에 around 처리를 한다.
	 * 
	 * @param joinPoint
	 *            ProceedingJoinPoint
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	@Around(value = "execution(* com..*Service.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) {

		Object[] args = joinPoint.getArgs();
		Object returnValue = null;

		String targetName = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		long startTime = 0;
		long endTime = 0;

		logger.debug("==========>>>>>>>>>> {}.{} Call", targetName, methodName);

		for (Object object : args) {
			if (object instanceof Map) {
				logger.debug("==========>>>>>>>>>> param : {}", ((Map<String, Object>) object).toString());
			} else if (object instanceof List) {
				logger.debug("==========>>>>>>>>>> param : {}", ((List<Object>) object).toString());
			}
		}

		startTime = System.currentTimeMillis();
		
		try {
			returnValue = joinPoint.proceed();
		} catch (Throwable e) {
			if (e instanceof BizException) {
				// 업무에서 BizException 으로 처리한 예외
				throw (BizException) e;
			} else {
				// 시스템 동작 중 발생한 예외를 로그 출력
				logger.error("Exception : ", e);

				// ControllerAop 로 CommonException 을 보냄
				throw new CommonException();
			}
		}

		endTime = System.currentTimeMillis();

		if (returnValue instanceof Map) {
			logger.debug("==========>>>>>>>>>> return : {}", ((Map<String, Object>) returnValue).toString());
		} else if (returnValue instanceof List) {
			logger.debug("==========>>>>>>>>>> return : {}", ((List<Object>) returnValue).toString());
		}

		logger.debug("==========>>>>>>>>>> {}.{}  Executed Time : {} Millis", targetName, methodName, this.getExecutedTime(startTime, endTime));

		return returnValue;
	}

	/**
	 * 처리 소요시간을 계산해 주는 메소드.
	 * 
	 * @author kimhoil
	 * @since 2016. 5. 18.
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

}