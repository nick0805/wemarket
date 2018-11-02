package com.core.exception;

/**
 * <pre>
 * Class Name : CommonException.
 * Description : 시스템 공통 예외 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 30.
 * @version 1.0.0
 * @see
 */
public class CommonException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6116315914363107307L;

	/**
	 * 기본 생성자.
	 */
	public CommonException() {
		super();
	}

	/**
	 * @param msg
	 *            String
	 */
	public CommonException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 *            Throwable
	 */
	public CommonException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param msg
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public CommonException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
