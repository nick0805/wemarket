<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:eval var="contextRoot" expression="@application['system.context.root']"/>

<html>
<body>
<form action="${contextRoot}/sample/Test/test.do" >
<input type="text" name="data" value="1234567890">
<input type="submit" value="전송">
</form>
</body>
</html>