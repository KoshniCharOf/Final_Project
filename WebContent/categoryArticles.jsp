<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:forEach items="${sessionScope.articles}" var="article">
				<a href="pickArticle?articleId=${article.id}" >"${article.title }"</a>
				<c:out value="${article.title }"></c:out> <br>
				<c:out value="${article.textContent }"></c:out> <br>
				<c:out value="${article.impressions }"></c:out> <br>
				<c:out value="${article.created }"></c:out> <br>
				
				<c:forEach items="${article.mediaFiles}" var="media">
					<img id="media" src="ShowMedia?mediaId=${media.media_id}"  width="100" height= auto>
				</c:forEach>
				<hr>
	 </c:forEach>

</body>
</html>