<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:eval var="contextRoot" expression="@application['system.context.root']"/>

<html>
<head>
<script type="text/javascript" src="${contextRoot}/js/common/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$.changeLocale=function(){
		
		var serializer = new XMLSerializer();		
		var sendData = {"language":$('#lang').val()};
		var result;
		$.ajax({
			type:"post",
			datatype:"json",
			contentType:"application/json; UTF-8",
			url:"${contextRoot}/sample/Test/testLocale.do",
			data:JSON.stringify(sendData),
			success : function(data) {
				result = JSON.stringify(data);
				console.log(result);
				$('#result').text(result);				
			},			
			error : function(req, status, error) {
				alert("ERROR!!!");
			}
		
		});
	}	
	
});
</script>
</head>
<body>
<h2>Test Locale</h2>
<select id="lang" name="lang">
	<option value="ko">한국어</option>
	<option value="en">ENGLISH</option>
	<option value="ja">日本語</option>
	<option value="zh">中國語</option>	
</select>
<input type="button" id="changeLocale" name="changeLocale" value="언어변경" onclick="$.changeLocale()">
<pre id="result" name="result">

</pre>

</body>
</html>