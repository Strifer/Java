
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
</head>
<%
String pickedColor = (String)request.getSession().getAttribute("pcolor"); 
if(pickedColor == null) {
pickedColor = "white";
}
%>

<body bgcolor="<%=pickedColor%>">
<h1>Index</h1>
<p>Welcome to the voting webapp. Please select the poll you wish to vote in:</p>
<ol>
		<c:forEach var="polls" items="${ polls }">
			<li><a href="glasanje?pollID=${ polls.pollID }">${ polls.title }</a></li>
		</c:forEach>
</ol>


</html>