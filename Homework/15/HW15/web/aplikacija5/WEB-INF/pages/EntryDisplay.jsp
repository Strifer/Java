<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		
		
		<title>BlogEntry</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		}
		</style>
	</head>

	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<h1><c:out value="${blogEntry.title}"/></h1>
      	<p><c:out value="${blogEntry.text}"/></p>
      	<c:if test="${ currentUserId !=null && currentUserId == blogEntry.creator.id }">
      	<a href="/aplikacija5/servleti/author/${ blogEntry.creator.nick }/edit?entryID=${ blogEntry.id }">Click to edit entry</a>
      	</c:if>
      	<c:if test="${!blogEntry.comments.isEmpty()}">
      	<ul>
      	<c:forEach var="e" items="${blogEntry.comments}">
        	<li><div style="font-weight: bold">[User=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      	</c:forEach>
      	</ul>
      	</c:if>
      	<a href="./">Back to blog entries</a>
      	<form action="/aplikacija5/servleti/newComment" method="post">
			<input type="hidden" name="entry" value="${ blogEntry.id }">
			<c:if test="${ currentUserId !=null }">
			<input type="hidden" name="anonymous" value="Anonymous">
			</c:if>
			Comment:<br>
			<textarea name="text" id="textarea" cols="45" rows="5" required></textarea>
			<input type="submit" name="metoda" value="Submit">
		</form>
		
		
		
	</body>
</html>