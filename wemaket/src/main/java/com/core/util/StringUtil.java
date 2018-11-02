package com.core.util;

/**
 * <pre>
 * Class Name : StringUtil.
 * Description : 문자열 제어를 위한 유틸 클레스. 업무파트 요청이 있을 경우 기능을 추가함.
 * 
 * </pre>
 * 
 * @author kimhoil
 * @since 2017. 3. 30.
 * @version 1.0.0
 * @see
 */
public final class StringUtil {

	/**
	 * 빈 문자열 <code>""</code>.
	 */
	public static final String EMPTY = "";

	/**
	 * 생성자 차단.
	 */
	private StringUtil() {

	}

	/**
	 * <p>
	 * String이 비었거나("") 혹은 null 인지 검증한다.
	 * </p>
	 *
	 * <pre>
	 *  StringUtil.isEmpty(null)      = true
	 *  StringUtil.isEmpty("")        = true
	 *  StringUtil.isEmpty(" ")       = false
	 *  StringUtil.isEmpty("bob")     = false
	 *  StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            - 체크 대상 스트링오브젝트이며 null을 허용함
	 * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 문자열이 null 이거나 공백이면 공백을 리턴한다.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String defaultIfEmpty(String str) {

		String result = "";

		if (str != null && str != "") {
			result = str;
		}

		return result;
	}

	/**
	 * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드.
	 * 
	 * @param object
	 *            원본 객체
	 * @return resultVal 문자열
	 */
	public static String getNullToString(Object object) {

		String string = "";

		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}

	/**
	 * 문자열이 null 이거나 공백이면 지정된 문자를 리턴한다.
	 * 
	 * @param str
	 *            String
	 * @param defaultStr
	 *            String
	 * @return String
	 */
	public static String getDefaultIfEmpty(String str, String defaultStr) {

		String result = "";

		if (str != null && str != "") {
			result = str;
		} else {
			result = defaultStr;
		}

		return result;
	}

	/**
	 * String Escape 처리.
	 * 
	 * @param src
	 *            String
	 * @return String
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * String UnEscape 처리.
	 *
	 * @param src
	 *            String
	 * @return String
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * <p>
	 * {@link String#toLowerCase()}를 이용하여 소문자로 변환한다.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.lowerCase(null)  = null
	 * StringUtil.lowerCase("")    = ""
	 * StringUtil.lowerCase("aBc") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            소문자로 변환되어야 할 문자열
	 * @return 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * <p>
	 * {@link String#toUpperCase()}를 이용하여 대문자로 변환한다.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.upperCase(null)  = null
	 * StringUtil.upperCase("")    = ""
	 * StringUtil.upperCase("aBc") = "ABC"
	 * </pre>
	 *
	 * @param str
	 *            대문자로 변환되어야 할 문자열
	 * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * <p>
	 * {@link String#toBr()}를 이용하여 <br />로 대체.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.toBr(null)  = ""
	 * StringUtil.toBr("")    = ""
	 * StringUtil.toBr("\r\n") = "<br />"
	 * </pre>
	 *
	 * @param str
	 *            변환되어야 할 문자열
	 * @return 변환된 문자열포함 전체 문자열, null이 입력되면 공백 리턴
	 */
	public static String toBr(String str) {
		if (str == null) {
			return "";
		}

		return str.replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
	}
	
}
