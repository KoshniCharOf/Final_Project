<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${article.title }</title>
</head>
<body>

	<c:set var="article" scope = "page" value="${sessionScope.article}" ></c:set>
				
	<h1><c:out value="${article.title }"></c:out></h1> <br>
	<c:out value="${article.textContent }"></c:out> <br>
	<c:out value="${article.impressions }"></c:out> <br>
	<c:out value="${article.created }"></c:out> <br>
	
	<c:forEach items="${article.mediaFiles}" var="media">
		<img id="media" src="ShowMedia?mediaId=${media.media_id}"  width="100" height= auto>
	</c:forEach>
	<hr>
	<c:forEach items="${article.comments}" var="comment">
		userId <c:out value="${comment.userId }">userId</c:out> <br>
		<c:out value="${comment.content }"></c:out> <br>
		likes:<c:out value="${comment.likes }">likes</c:out> 
		dislikes:<c:out value="${comment.dislikes }">dislikes</c:out> <br>
		timeCreated:<c:out value="${comment.timeCreated }">timeCreated</c:out> <br>
		aproved:<c:out value="${comment.aproved }">aproved</c:out> <br>
		<c:if test="${user!=null }">
			<a href="Vote?vote=like&commentId=${comment.commentId }"><button>like</button></a>
			<a href="Vote?vote=dislike&commentId=${comment.commentId }"><button>dislike</button></a>
		</c:if>
	</c:forEach>
	
	<c:if test="${user!=null }">
		<form action="Comment" method="post" >
			<input type="text" name ="comment" placeholder = "comment here">
			<input type="submit" value="comment">
		</form>
	</c:if>


</body>
</html>