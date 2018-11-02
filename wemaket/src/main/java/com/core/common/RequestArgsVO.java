package com.core.common;

import java.util.Map;

/**
 * <pre>
 * Class Name : RequestArgsVO.
 * Description : RequestArgsVO.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 4. 6.
 * @version 1.0.0
 * @see
 */
public class RequestArgsVO {

	/**
	 * Map<String, Object> paramData.
	 */
	private Map<String, Object> paramData = null;

	/**
	 * Map<String, Object> requestData.
	 */
	private Map<String, Object> requestData = null;

	/**
	 * Map<String, Object> userData.
	 */
	private Map<String, Object> userData = null;

	/**
	 * Map<String, Object> getHeaderInfo.
	 */
	private Map<String, Object> headerInfo = null;
	
	
	/**
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getParamData() {
		return paramData;
	}

	
	/**
	 * @param param
	 *            Map<String, Object>
	 */
	public void setParamData(Map<String, Object> param) {
		this.paramData = param;
	}

	/**
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getRequestData() {
		return requestData;
	}

	/**
	 * @param request
	 *            Map<String, Object>
	 */
	public void setRequestData(Map<String, Object> request) {
		this.requestData = request;
	}

	/**
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getUserData() {
		return userData;
	}

	/**
	 * @param userInfo
	 *            Map<String, Object>
	 */
	public void setUserData(Map<String, Object> userInfo) {
		this.userData = userInfo;
	}


	public Map<String, Object> getHeaderInfo() {
		return headerInfo;
	}


	public void setHeaderInfo(Map<String, Object> headerInfo) {
		this.headerInfo = headerInfo;
	}
	

}