<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Log in</title>
	</head>
	<body>
		<c:if test="${ requestScope.error != null }">
			<h1 style="color: red">Sorry,  ${requestScope.error }</h1>
		</c:if>
		<form action="Login" method="post">
			Username <input type="text" name="username"><br>
			Password <input type="password" name="password"><br>
			<input type="submit" value="Login"><br>
		</form>
		Don`t have an account yet? Please <a href="register.jsp">register</a>.
		<a href="index.jsp">Home</a>
	</body>
</html>