package com.core.common;

/**
 * <pre>
 * Class Name : SystemConstant.
 * Description : 시스템 공통 컨스턴츠.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 31.
 * @version 1.0.0
 * @see
 */
public final class SystemConstant {

	/**
	 * 기본 오류.
	 */
	public static final String ERROR_DEFAULT = "-1";

	/**
	 * 세션 오류.
	 */
	public static final String ERROR_SESSION = "-9";

	/**
	 * 컨텐트 타입.
	 */
	public static final String CONTENT = "contentType";

	/**
	 * 로케일.
	 */
	public static final String LOCALE = "locale";

	/**
	 * 사용자 정보.
	 */
	public static final String USER_DATA = "userData";

	/**
	 * 사용자 정보.
	 */
	public static final String HEADER_INFO = "headerInfo";
	
	/**
	 * 에러코드.
	 */
	public static final String ERROR_CODE = "errorCode";
	
	/**
	 * 에러메시지.
	 */
	public static final String ERROR_MSG = "errorMsg";

	/**
	 * 메시지 인자.
	 */
	public static final String MSM_ARG = "arg";

	/**
	 * json 컨텐트 타입.
	 */
	public static final String JSON_CONTENT_TYPE = "application/json";
	//public static final String JSON_CONTENT_TYPE = "text/json";

	/**
	 * 생성자 차단.
	 */
	private SystemConstant() {
	}
}