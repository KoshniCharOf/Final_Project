<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User page</title>
</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		
		<c:set var = "user" value="${sessionScope.user }"></c:set>
		<c:out value="${user.username }"></c:out>
		<c:out value="${user.email }"></c:out>
		<c:if test="${user.avatar_url!=null }">
			<c:out value="${user.avatar_url }"></c:out>
			<img id="avatar" src="avatar">
		</c:if>
		<form action="avatarUpload" method="post" enctype="multipart/form-data">
			Avatar<input type="file" name="avatar"><br>
			<input type="submit" value="upload"><br>
		</form>
		
		
		<c:if test="${user.admin }">
			<jsp:include page="admin.jsp"></jsp:include>
		</c:if>
	</body>
</html>