/**
 * 
 */
package com.wemarket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wemarket.service.WemarketTestService;

/**
 * @author Nick
 *
 */
@Controller
@RequestMapping("/wemarket")
public class WemarketTestController {
	/**
	 * Logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WemarketTestService service;

	/**
	 * 테스트 페이지
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView test(HttpServletRequest request, @RequestParam Map<String, Object> param) {

		ModelAndView view = new ModelAndView();
		logger.debug("Main start");
		view.setViewName("/wemarket/wemarketTest");

		return view;
	}

	/**
	 * 출력 결과
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getTxt")
	@ResponseBody
	public Map<String, Object> getTxt(HttpServletRequest request, @RequestBody Map<String, Object> param) {

		Map<String, Object> result = new HashMap<String, Object>();

		// URL의 TXT를 몫과 나머지 로 구분하여 가져오기
		result = service.getTxt(param);
		result.put("errorCode", "0");
		return result;
	}
}
