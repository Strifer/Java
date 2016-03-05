<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registration</title>
		
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
		Registration
		</h1>

		<form action="save" method="post">
		
		Nick <input type="text" name="nick" value='<c:out value="${zapis.nick}"/>' maxlength="30" ><br>
		<c:if test="${zapis.hasError('nick')}">
		<div class="greska"><c:out value="${zapis.getError('nick')}"/></div>
		</c:if>
		
		Password <input type="password" name="pass" size="30" maxlength="30"><br>
		<c:if test="${zapis.hasError('pass')}">
		<div class="greska"><c:out value="${zapis.getError('pass')}"/></div>
		</c:if>

		First Name <input type="text" name="firstName" value='<c:out value="${zapis.firstName}"/>' size="30" maxlength="30"><br>
		<c:if test="${zapis.hasError('firstName')}">
		<div class="greska"><c:out value="${zapis.getError('firstName')}"/></div>
		</c:if>
		
		Last Name <input type="text" name="lastName" value='<c:out value="${zapis.lastName}"/>' size="30" maxlength="30"><br>
		<c:if test="${zapis.hasError('lastName')}">
		<div class="greska"><c:out value="${zapis.getError('lastName')}"/></div>
		</c:if>
		
		EMail <input type="text" name="email" value='<c:out value="${zapis.email}"/>' size="40" maxlength="30"><br>
		<c:if test="${zapis.hasError('email')}">
		<div class="greska"><c:out value="${zapis.getError('email')}"/></div>
		</c:if>
		
		<input type="submit" name="metoda" value="Submit"><br>
		<input type="submit" name="metoda" value="Cancel">
		
		</form>

	</body>
</html>