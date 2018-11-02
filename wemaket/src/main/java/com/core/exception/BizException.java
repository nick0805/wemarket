package com.core.exception;

/**
 * <pre>
 * Class Name : BizException.
 * Description : 업무용 예외 클래스.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 30.
 * @version 1.0.0
 * @see
 */
public class BizException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -752041893732009003L;

	/**
	 * message 처리용 String 의 배열 변수.
	 */
	private String[] arg = null;

	/**
	 * @param msg
	 *            String
	 */
	public BizException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 *            Throwable
	 */
	public BizException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param msg
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public BizException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 *            String
	 * @param args
	 *            String[]
	 */
	public BizException(String msg, String[] args) {
		super(msg);
		this.arg = args;
	}

	/**
	 * @return String[]
	 */
	public String[] getArg() {
		return arg;
	}

	/**
	 * @param args
	 *            String[]
	 */
	public void setArg(String[] args) {
		this.arg = args;
	}

}
