/**
 * 
 */
package com.wemarket.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Nick
 *
 */
@Service("WemarketTestService")
public class WemarketTestService {
	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public Map<String,Object> getTxt(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String url = String.valueOf(param.get("url"));
		String type = String.valueOf(param.get("type"));
		int num = Integer.parseInt(String.valueOf(param.get("num")));
		
		// URL 내에 HTML 가져오기
		String html = this.getUrlHtml(url);
		if("html".equals(type)) {
			// html 태그 제거
			html = this.replaceHtml(html);
		}
		// 영문, 숫자 이외 제거
		html = this.replaceAlpaAndNumOther(html);
		
		// 영어 대문자, 영어 소문자, 숫자 순으로 정력
		html = this.getSort(html);
		
		// 몫 구하기
		int mok = html.length() / num;
		resultMap.put("mok", html.substring(0, mok * num));
		resultMap.put("rem", html.substring(mok * num, html.length()));
		
		return resultMap;
	}
	
	/**
	 * URL 내에 HTML 가져오기
	 * @param str
	 * @return
	 */
	private String getUrlHtml(String str) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(str);
			URLConnection con = url.openConnection();
			BufferedReader buf = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp;
			while ((temp = buf.readLine()) != null) {
				temp = temp.trim();
				if (!"".equals(temp)) {
					result.append(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * html 태그 제거
	 * @param str
	 * @return
	 */
	private String replaceHtml(String str) {
		Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
		Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
		Pattern WHITESPACE = Pattern.compile("\\s\\s+");
		Matcher m;
		m = SCRIPTS.matcher(str);
		str = m.replaceAll("");
		m = STYLE.matcher(str);
		str = m.replaceAll("");
		m = TAGS.matcher(str);
		str = m.replaceAll("");
		m = ENTITY_REFS.matcher(str);
		str = m.replaceAll("");
		m = WHITESPACE.matcher(str);
		str = m.replaceAll("");
		logger.debug(str);
		return str;
	}

	/**
	 * 영문, 숫자 이외 제거
	 * @param str
	 * @return
	 */
	private String replaceAlpaAndNumOther(String str) {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher m;
		m = pattern.matcher(str);
		str = m.replaceAll("");
		
		return str;
	}
	
	/**
	 * 영어 대문자, 영어 소문자, 숫자 순으로 정력
	 * @param srt
	 * @return
	 */
	private String getSort(String str) {
		StringBuffer result = new StringBuffer();
		Matcher m;
		
		Pattern alphaPattern = Pattern.compile("[^a-zA-Z]");
		Pattern numPattern = Pattern.compile("[^0-9]");
		m = alphaPattern.matcher(str);
		String[] alpha = m.replaceAll("").split("");
		m = numPattern.matcher(str);
		String[] num = m.replaceAll("").split("");
		
		Arrays.sort(alpha);
		Arrays.sort(alpha, String.CASE_INSENSITIVE_ORDER);
		Arrays.sort(num);
		
		int maxNum = alpha.length > num.length ? alpha.length : num.length;
		
		for(int i = 0; i < maxNum; i++) {
			if(alpha.length > i) {
				result.append(alpha[i]);
			}
			if(num.length > i) {
				result.append(num[i]);
			}
		}
		logger.debug("Sort Html : " + result.toString());
		return result.toString();
	}
	
}
