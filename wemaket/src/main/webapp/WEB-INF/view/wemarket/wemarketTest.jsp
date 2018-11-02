<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:eval var="contextRoot" expression="@application['system.context.root']" />
<script type="text/javascript" src="${contextRoot}/resource/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${contextRoot}/resource/js/common.js"></script>
<script type="text/javascript" src="${contextRoot}/resource/js/comm.js"></script>
<script type="text/javascript">
	var getCont = function(){
		var param = {};
		param.url = $('#url').val();
		param.type = $('#type').val();
		param.num = $('#num').val();
		var url = "${contextRoot}/wemarket/getTxt";
		comAjax(url, param, fn_callback);
	}

	var fn_callback = function(req){
	    if(req.errorCode == "0"){
	    	$('#mok').html(req.mok);
	    	$('#rem').html(req.rem);
	    }
	    else{
	    	alert("잘못된 URL 입니다.");
	    }
	}
	
</script>
</head>
<body>
url : <input type="text" id="url" name="url" value="http://www.wemakeprice.com/"/>
<br>
type : <select id="type" name="type">
	<option value="txt" selected="selected">TXT</option>
	<option value="html">HTML</option>
	</select>
<br>
출력 구분 : <input type="text" id="num" name="num" value="1"/> <input type="button" onclick="getCont();" value="출력">
<br>
몫 : <span id="mok"></span>
<br>
나머지 : <span id="rem"></span>
</html>