package com.core.exception;

import java.util.Map;

/**
 * <pre>
 * Class Name : JsonViewException.
 * Description : JsonView 처리를 위한 예외 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 30.
 * @version 1.0.0
 * @see
 */
public class JsonViewException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6031411229653919152L;

	/**
	 * request data 처리용 Map 변수.
	 */
	private Map<String, Object> requestDataMap = null;

	/**
	 * 기본 생성자.
	 */
	public JsonViewException() {
		super();
	}

	/**
	 * @param msg
	 *            String
	 */
	public JsonViewException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 *            Throwable
	 */
	public JsonViewException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param msg
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public JsonViewException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param requestData
	 *            Map<String, Object>
	 */
	public JsonViewException(Map<String, Object> requestData) {
		this.requestDataMap = requestData;
	}

	/**
	 * @param msg
	 *            String
	 * @param requestData
	 *            Map<String, Object>
	 */
	public JsonViewException(String msg, Map<String, Object> requestData) {
		super(msg);
		this.requestDataMap = requestData;
	}

	/**
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getRequestData() {
		return requestDataMap;
	}

	/**
	 * @param requestData
	 *            Map<String, Object>
	 */
	public void setRequestData(Map<String, Object> requestData) {
		this.requestDataMap = requestData;
	}

}
