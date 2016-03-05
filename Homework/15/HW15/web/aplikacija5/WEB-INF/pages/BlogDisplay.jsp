<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Blog</title>

	</head>

	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<h1>
		Blog of ${ selected.nick }
		</h1>
		
		<h1>
		Blog entries
		</h1>
		<c:forEach var="e" items="${ entries }">
		<li><div style="font-weight: bold"> <a href="/aplikacija5/servleti/author/${ selected.nick }/${ e.id }">${ e.title }</a></div></li>
		</c:forEach>
		<c:if test="${ currentUserId ==selected.id }">
		<a href="/aplikacija5/servleti/author/${ selected.nick }/new">New entry</a>
		</c:if>
		<br>
		<a href="/aplikacija5/">Click to go back to main</a>
		
		
		
	</body>
</html>