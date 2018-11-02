package com.core.common;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;


/**
 * <pre>
 * Class Name : ThreadLookup.
 * Description : 서버가 논리 이중화 구성일 경우 로그의 분리를 위해 사용하는 log4j2 ThreadLookup 처리 클래스
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 4. 12.
 * @version 1.0.0
 * @see
 */
@Plugin(name = "thread", category = StrLookup.CATEGORY)
public class ThreadLookup implements StrLookup {

	@Override
	public String lookup(String key) {
		return this.getServerName(Thread.currentThread().getName());
	}

	@Override
	public String lookup(LogEvent event, String key) {

		String result = null;

		if (event.getThreadName() == null) {
			result = this.getServerName(Thread.currentThread().getName());
		} else {
			result = this.getServerName(event.getThreadName());
		}

		return result;
	}

	/**
	 * Thread 에서 서버명을 가저오는 메소드.
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	private String getServerName(String name) {

		String result = null;

		if (name.indexOf("[") > 0) {
			result = name.substring(name.indexOf("[") + 1, name.length() - 1);
		}

		if (result == null) {
			result = "server1";
		} else {
			result = result.split("-")[0];
		}

		return result;
	}

}