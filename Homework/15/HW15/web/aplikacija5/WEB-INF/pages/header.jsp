<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${ currentUserId != null }">
		<table border="1" style="width:20%">
		  				<tr>
		    				<td>Name</td>
		    				<td>${ currentUserFn }</td>
		  				</tr>
		  				<tr>
		   					<td>Lastname</td>
		    				<td>${ currentUserLn }</td>
		 				</tr>
		</table>
		<a href="/aplikacija5/servleti/logout">Logout</a>
	</c:when>
	
	<c:otherwise>
		Not logged in
	</c:otherwise>
	
</c:choose>