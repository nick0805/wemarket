package com.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.core.message.ExposedMessageSource;

public class uploadFileUtil {

	public uploadFileUtil() {
	}

	public Map<String, String> saveFile(HttpServletRequest req, HttpServletResponse rep) throws Exception {

		// MultipartHttpServletRequest 생성
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
		Iterator iter = mhsr.getFileNames();
		MultipartFile mfile = null;
		String fieldName = "";
		List resultList = new ArrayList(); 
		Map returnFileInfo = new HashMap<String, Object>();
		ExposedMessageSource exposedMessageSource = new ExposedMessageSource();
		
		Locale locale = Locale.getDefault();
		
		String path = exposedMessageSource.getMessage("uploadFile.path", null, locale);
		// 디레토리가 없다면 생성 
		File dir = new File(path);
		
		if (!dir.isDirectory()) {
			dir.mkdirs(); 
		} 
		
		// 값이 나올때까지 
		while (iter.hasNext()) { 
			fieldName = (String) iter.next(); // 내용을 가져와서 
			mfile = mhsr.getFile(fieldName); 
			String  origName; 
			origName = new String(mfile.getOriginalFilename().getBytes("8859_1"), "UTF-8"); //한글꺠짐 방지 
			
			// 파일명이 없다면
			if ("".equals(origName)) { continue; } 
			
			// 파일 명 변경(uuid로 암호화)
			String ext = origName.substring(origName.lastIndexOf('.')); // 확장자 
			String saveFileName = getUuid() + ext; 
			
			// 설정한 path에 파일저장 
			File serverFile = new File(path + File.separator +  saveFileName);
			mfile.transferTo(serverFile); 
			
			Map file = new HashMap();
		    file.put("origName", origName);
		    file.put("sfile", serverFile);
		    resultList.add(file); 
		}
		
		returnFileInfo.put("files", resultList);
		return returnFileInfo;
 

	}
	
	public static String getUuid() { 
		return UUID.randomUUID().toString().replaceAll("-", ""); 
	}

}
