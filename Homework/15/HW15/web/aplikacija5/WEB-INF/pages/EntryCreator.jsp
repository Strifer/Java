<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Editor</title>
		
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
		<h1>
		BlogMaker
		<c:out value="${ action }"></c:out>
		</h1>
		
		<c:choose>
			
			<c:when test="${ action == 'new' }">
			<form action="/aplikacija5/servleti/newBlog" method="post">
			<input type="hidden" name="nick" value="${ nick }">
			Title <input type="text" name="title" maxlength="200" required><br>
			<textarea name="text" id="textarea" cols="45" rows="5" accept-charset="UTF-8" maxlength="4096" required></textarea>
			<input type="submit" name="metoda" value="Submit">
			<input type="submit" name="metoda" value="Cancel">
			</form>
			</c:when>
			<c:when test="${ action == 'edit' }">
			<form action="/aplikacija5/servleti/editBlog" method="post"">
			Title <input type="text" name="title" value="${ blogEntry.title }" maxlength="200" required><br>
			<input type="hidden" name="id" value="${ blogEntry.id }">
			<textarea name="text" id="textarea" cols="45" rows="5" accept-charset="UTF-8" maxlength="4096" required>${ blogEntry.text }</textarea>
			<input type="submit" name="metoda" value="Submit">
			<input type="submit" name="metoda" value="Cancel">
			</form>
			</c:when>
			<c:otherwise>
			<jsp:include page="error.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
		
		
		
	</body>
</html>