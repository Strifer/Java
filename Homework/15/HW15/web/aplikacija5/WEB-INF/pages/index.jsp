<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Main page</title>
		
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
		<h1>
		Main page
		</h1>
		
		<jsp:include page="header.jsp"></jsp:include>
		<c:choose>
			<c:when test="${ currentUserId != null }">
			
			</c:when>
			
			<c:otherwise>
			<h1>Login</h1>
			<form action="login" method="post">
				Nick <input type="text" name="nick" value='<c:out value="${logZapis.nick}"/>' maxlength="30" ><br>
				<c:if test="${logZapis.hasError('nick')}">
					<div class="greska"><c:out value="${logZapis.getError('nick')}"/></div>
				</c:if>
		
				Password <input type="password" name="pass" size="30" maxlength="30"><br>
				<c:if test="${logZapis.hasError('pass')}">
					<div class="greska"><c:out value="${logZapis.getError('pass')}"/></div>
				</c:if>
				<input type="submit" name="metoda" value="Submit">
				</form>
			
			<br>
			<a href="register">Make a new account</a><br>
			</c:otherwise>
		</c:choose>
		<h1>
		Blogovi
		</h1>
		<c:forEach var="u" items="${ users }">
		<li><div style="font-weight: bold"> <a href="author/${ u.nick }">[Author: <c:out value="${ u.nick }"></c:out>]</a></div></li>
		</c:forEach>
		
		
		
	</body>
</html>